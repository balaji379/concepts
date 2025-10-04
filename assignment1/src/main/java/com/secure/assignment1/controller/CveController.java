package com.secure.assignment1.controller;

import com.secure.assignment1.entity.Cve;
import com.secure.assignment1.entity.CveData;
import com.secure.assignment1.repo.CveDataRepo;
import com.secure.assignment1.repo.CveRepo;
import com.secure.assignment1.service.NvdServiceToStoreAsJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/cves")
public class CveController {

    @Autowired
    private CveDataRepo cveRepository;

    @Autowired
    NvdServiceToStoreAsJson nvdServiceToStoreAsJson;

    @GetMapping("/")
    public ResponseEntity<String> fetchAll(){
        nvdServiceToStoreAsJson.storeAsJson();
        return ResponseEntity.ok().body("data fetched from nvd server");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CveData> getCveById(@PathVariable String id) {
        return cveRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/modified")
    public List<CveData> getCvesModifiedInLastNDays(@RequestParam LocalDateTime date) {
        System.out.println("date is " +date);
        return cveRepository.findBylastModifieldDate(date);
    }
}