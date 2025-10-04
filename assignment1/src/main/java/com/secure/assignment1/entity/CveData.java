package com.secure.assignment1.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.secure.assignment1.config.JsonConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CveData {

    @Id
    String cveId;
    private LocalDateTime lastModifieldDate;
    @Column(columnDefinition = "JSON")
    @Convert(converter = JsonConverter.class)
    private JsonNode json;
}
