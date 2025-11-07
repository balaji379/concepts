package com.spring_batch.nvd_task.cofig;

import com.spring_batch.nvd_task.entity.NvdEntity;
import com.spring_batch.nvd_task.modal.Cve;
import com.spring_batch.nvd_task.modal.Description;
import jakarta.persistence.Embedded;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NvdReader implements ItemReader<NvdEntity> {
    private int startIndex;
    Iterator<NvdEntity> currentBatch;
    private String url = "https://services.nvd.nist.gov/rest/json/cves/2.0?resultsPerPage=200&startIndex=%d";
    private boolean end = false;
    private final int PAGE_SIZE = 200;
    private final RestTemplate restTemplate = new RestTemplate();

    public NvdReader() {
        startIndex = 0;
    }


    @Override
    public NvdEntity read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (end)
            return null;
        if (currentBatch == null || !currentBatch.hasNext()) {
            List<NvdEntity> nvdEntities = fetchApi();
            if (nvdEntities.isEmpty()) {
                end = true;
                return null;
            } else currentBatch = nvdEntities.iterator();
        }

        return currentBatch.next();
    }

    public List<NvdEntity> fetchApi() throws Exception {
        String endPoint = String.format(url, startIndex);
        List<NvdEntity> nvdEntities = new ArrayList<>();

        try {
            Map<String, Object> response = restTemplate.getForObject(endPoint, Map.class);
            if (response != null && response.containsKey("vulnerabilities")) {
                List<Map<String, Object>> vul = (List<Map<String, Object>>) (response.get("vulnerabilities"));
                for (Map<String, Object> inner : vul) {
                    Map<String, Object> cveMap = (Map<String, Object>) inner.get("cve");
                    List<Description> descriptions = new ArrayList<>();
                    if (cveMap.containsKey("descriptions")) {
                        List<Map<String, String>> descriptionList = (List<Map<String, String>>) cveMap.get("descriptions");
                        for (Map<String, String> descriptionMap : descriptionList) {
                            descriptions.add(Description.builder()
                                    .lang(descriptionMap.get("lang"))
                                    .value(descriptionMap.get("value"))
                                    .build());
                        }
                    }
                    Cve cve = Cve.builder()
                            .cveId(cveMap.get("id").toString())
                            .sourceIdentifier(cveMap.get("sourceIdentifier").toString())
                            .published(parseDate(cveMap.get("published").toString()))
                            .lastModified(parseDate(cveMap.get("lastModified").toString()))
                            .vulnStatus(cveMap.get("vulnStatus").toString())
                            .descriptionList(descriptions)
                            .build();
                    nvdEntities.add(NvdEntity.builder()
                            .cve(cve)
                            .build());

                }
                Integer responseCount = Integer.parseInt(response.get("totalResults").toString());
                startIndex += PAGE_SIZE;
                if (response == null || responseCount <= startIndex) {
                    end = true;
                }
            }
        } catch (Exception e) {
            Thread.sleep(2000);
            return new ArrayList<>();
        }
        return nvdEntities;
    }

    private LocalDateTime parseDate(String dateStr) {
        try {
            if (dateStr.endsWith("Z"))
                return OffsetDateTime.parse(dateStr).toLocalDateTime();
            return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        } catch (Exception e) {
            return null;
        }
    }
}
