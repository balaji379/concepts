package com.secure.assignment1.repo;

import com.secure.assignment1.entity.Cve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CveRepo extends JpaRepository<Cve,String> {
    List<Cve> findByLastModifiedDateAfter(LocalDateTime date);
}
