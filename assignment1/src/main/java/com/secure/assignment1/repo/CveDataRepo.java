package com.secure.assignment1.repo;

import com.secure.assignment1.entity.CveData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CveDataRepo extends JpaRepository<CveData, String> {
    public List<CveData> findBylastModifieldDate(LocalDateTime lastModifieldDate);
}
