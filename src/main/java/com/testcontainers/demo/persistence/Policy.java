package com.testcontainers.demo.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "policy")
@Data
@NoArgsConstructor
public class Policy {

    @Id
    private Integer policyId;
    private String policyNumber;
    private String ownerFirstName;
    private String ownerLastName;
    private LocalDate effectivityDate;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdate;

}
