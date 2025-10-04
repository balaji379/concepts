package com.secure.assignment1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secure.assignment1.entity.CveData;
import com.secure.assignment1.repo.CveDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class NvdServiceToStoreAsJson {
    @Autowired
    RestTemplate httpCall;

    @Autowired
    CveDataRepo cveDataRepo;

    private static final String NVD_API_URL = "https://services.nvd.nist.gov/rest/json/cves/2.0";
    private static final int PAGE_SIZE = 20;

    public void storeAsJson() {
        ObjectMapper mapper = new ObjectMapper();
        int startIndex = 0;
        String url = NVD_API_URL + "?startIndex=" + startIndex + "&resultsPerPage=" + PAGE_SIZE;
        JsonNode node = httpCall.getForEntity(url, JsonNode.class).getBody();
        JsonNode vulnerabilities = node.get("vulnerabilities");

        for (JsonNode json : vulnerabilities) {
            JsonNode cve = json.get("cve");
            if (cve == null)
                continue;
            String id = cve.get("id").asText();
            LocalDateTime lastModified = null;
            if (cve.has("lastModified")) {
                lastModified = LocalDateTime.parse(cve.get("lastModified").asText());
            }
            CveData cveData = CveData.builder()
                    .cveId(id)
                    .json(cve)
                    .lastModifieldDate(lastModified)
                    .build();
            cveDataRepo.save(cveData);
        }
    }
}
