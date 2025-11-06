package com.spring_batch.nvd_task.repo;

import com.spring_batch.nvd_task.entity.NvdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NvdRepo extends JpaRepository<NvdEntity,Integer> {
}
