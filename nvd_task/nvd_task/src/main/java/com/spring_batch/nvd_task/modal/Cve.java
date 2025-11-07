package com.spring_batch.nvd_task.modal;

import jakarta.persistence.*;
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
     String cveId;
     String sourceIdentifier;
     LocalDateTime published;
     LocalDateTime lastModified;
     String vulnStatus;
    @ElementCollection
    @CollectionTable(name = "cve_descriptions", joinColumns = @JoinColumn(name = "cve_id"))
     List<Description> descriptionList;
}
