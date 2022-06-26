package com.backend.demo.model;

import com.backend.demo.enums.ContractTypes;
import com.backend.demo.enums.ExperiencieLevel;
import com.backend.demo.enums.Remote;
import com.backend.demo.enums.SizeCompany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.log4j2.ColorConverter;

import javax.persistence.*;
import javax.print.DocFlavor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "spring_boot_jobs_vaga")
public class Job {
    @Id
    @SequenceGenerator(name = "vacancies_sequence", sequenceName = "vacancies_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacancies_sequence")
    private Long id;

    @NotNull(message = "falta o id do usuario")
    private Long id_user;

    @NotBlank(message = "o titulo dava vaga é obrigatorio")
    @Size(min = 1, max = 80, message = "titulo deve ter entre 1 e 80 caracteres")
    private String title;

    @NotNull(message = "tamanho da empresa precisa ser especificado")
    @Enumerated(EnumType.STRING)
    private SizeCompany size_company;

    @NotNull(message = "salario a ser pago é obrigatorio")
    private String salary;

    @NotNull(message = "nivel de experiencia obrigatorio")
    @Enumerated(EnumType.STRING)
    private ExperiencieLevel experience_level;

    @NotNull(message = "tipo de contrato obrigatorio")
    @Enumerated(EnumType.STRING)
    private ContractTypes types_contract;

    @NotNull(message = "necessario dizer se a vaga é remoto ou não")
    @Enumerated(EnumType.STRING)
    private Remote remote;

    @NotBlank(message = "nome da empresa obrigatorio")
    @Size(min = 16, max = 60, message = "o nome da compania deve ter entre 16 e 60 caracteres")
    private String name_company;

    @NotBlank(message = "beneficios não pode estár vazio")
    @Size(min = 1, max = 600)
    private String benefits;

    @NotBlank(message = "responsabilidade não pode estár vazio")
    @Size(min = 1, max = 600)
    private String responsibilities;

    @NotBlank(message = "requerimento não pode estár vazio")
    @Size(min = 1, max = 600)
    private String requirements;

    private String created_at;

    private String updated_at;
    @NotNull
    @NotBlank(message = "dias a ser expirado, não pode estár vazio")
    @Length(min = 1, max = 2)
    private String expired_days;

    @NotNull
    private String token;

    public Job() {

    }

    @Transient
    private List<String> tech; // this is the data as convenient for you to use

    @Column(nullable = false)
    private String techs; // this is the database column format

    @PrePersist
    @PreUpdate
    private void preUpdate() {
        if (tech == null) {
            techs = "";
            return;
        }
        StringBuilder sb = new StringBuilder();
        String glue = "";
        for (String fn : tech) {
            sb.append(glue).append(fn);
            glue = ";";
        }
        techs = sb.toString();

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d/MM/yyyy");

        this.created_at = LocalDate.now().format(formatterDate);
        this.updated_at = LocalDate.now().format(formatterDate);
    }

    @PostLoad
    private void fromTechColumn() {
        tech = Arrays.asList(techs.split(";"));
    }

}
