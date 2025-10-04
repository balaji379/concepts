package com.example.protobuffer.repo;

import com.example.protobuffer.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employees,Integer> {
}
