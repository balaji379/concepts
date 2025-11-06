package com.spring_batch.nvd_task.modal;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cve {
    private String cveId;
    private String sourceIdentifier;
    private LocalDateTime published;
    private LocalDateTime lastModified;
    private String vulnStatus;
    @Embedded
    private List<Description> descriptionList;
}
