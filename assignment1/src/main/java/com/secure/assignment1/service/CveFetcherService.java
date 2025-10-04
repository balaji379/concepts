package com.secure.assignment1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.secure.assignment1.entity.Cve;
import com.secure.assignment1.repo.CveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CveFetcherService {

    private static final String NVD_API_URL = "https://services.nvd.nist.gov/rest/json/cves/2.0";
    private static final int PAGE_SIZE = 2000;

    @Autowired
    private CveRepo cveRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void fetchAndStoreCves() {
        int startIndex = 0;
        while (true) {
            String url = NVD_API_URL + "?startIndex=" + startIndex + "&resultsPerPage=" + PAGE_SIZE;
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
            JsonNode root = response.getBody();

            if (root == null || !root.has("vulnerabilities")) break;

            List<Cve> cveList = new ArrayList<>();
            for (JsonNode item : root.get("vulnerabilities")) {
                JsonNode cveNode = item.get("cve");
                String cveId = cveNode.get("id").asText();
                String description = cveNode.get("descriptions").get(0).get("value").asText();
                LocalDateTime publishedDate = LocalDateTime.parse(cveNode.get("published").asText());
                LocalDateTime lastModifiedDate = LocalDateTime.parse(cveNode.get("lastModified").asText());

                Cve cve = new Cve();
                cve.setId(cveId);
                cve.setDescription(description);
                cve.setPublishedDate(publishedDate);
                cve.setLastModifiedDate(lastModifiedDate);

                cveList.add(cve);
            }

            cveRepository.saveAll(cveList);

            startIndex += cveList.size();
            if (startIndex >= root.get("totalResuIndelts").asInt()) break;
        }
    }
}

