package com.secure.assignment1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cve {
    @Id
    private String id; // CVE ID

    @Column(length = 5000)
    private String description;

    private LocalDateTime publishedDate;

    private LocalDateTime lastModifiedDate;
}
