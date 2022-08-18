package com.backend.demo.model;

import com.backend.demo.enums.ContractTypes;
import com.backend.demo.enums.ExperiencieLevel;
import com.backend.demo.enums.Remote;
import com.backend.demo.enums.SizeCompany;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.log4j2.ColorConverter;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.print.DocFlavor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "spring_job")
public class Job {
    @Id
    @SequenceGenerator(name = "job_sequence", sequenceName = "job_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_sequence")
    private Long id;

    @NotNull(message = "falta o id do usuario")

//    @ManyToMany
//    private Set<User> id_user;
    private Long user_id;

    @NotBlank(message = "o titulo é obrigatorio")
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
    @Size(min = 1, message = "o nome da compania deve ter entre 16 e 60 caracteres")
    private String name_company;

    @NotBlank(message = "beneficios não pode estár vazio")
    @Size(min = 1)
    private String benefits;

    @NotBlank(message = "responsabilidade não pode está vazio")
    @Size(min = 1)
    private String responsibilities;

    @NotBlank(message = "requerimento não pode estár vazio")
    @Size(min = 1)
    private String requirements;

    @NotBlank(message = "techs requerida")
    private String techs;

    private String updated_at;
    private String created_at;

    @NotNull
    @NotBlank(message = "necessário escolher quando será expirado a postagem")
    @Length(min = 1, max = 2)
    private String expired_days;

    @NotBlank(message = "token não provido")
    private String token;


//    @ManyToOne(fetch = FetchType.EAGER)
//    private User authorPost;


    public Job() {

    }

    @PrePersist
    @PreUpdate
    private void preUpdate() {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d/MM/yyyy");

        this.created_at = LocalDate.now().format(formatterDate);
        this.updated_at = LocalDate.now().format(formatterDate);

    }


}
