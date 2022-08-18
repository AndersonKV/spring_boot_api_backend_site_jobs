package com.backend.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "spring_matching")
public class Matching {

    @Id
    @SequenceGenerator(name = "matching_sequence", sequenceName = "matching_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matching_sequence")
    private Long id;
    @NotNull
    private Long id_user;
    @NotNull
    private Long id_job;

    private String created_at;

    private String updated_at;

    @NotNull
    private String token;

    @PrePersist
    @PreUpdate
    private void setDate() {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d/MM/yyyy");
        this.created_at = LocalDate.now().format(formatterDate);
        this.updated_at = LocalDate.now().format(formatterDate);
    }

}
