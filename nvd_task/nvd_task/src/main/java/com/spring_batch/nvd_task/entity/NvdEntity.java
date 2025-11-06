package com.spring_batch.nvd_task.entity;

import com.spring_batch.nvd_task.modal.Cve;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Generated;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NvdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Embedded
    private Cve cve;
}
