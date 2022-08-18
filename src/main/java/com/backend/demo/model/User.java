package com.backend.demo.model;

import javax.validation.constraints.*;

import com.backend.demo.DTO.UserUpdateDTO;
import com.backend.demo.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.Id;

import javax.persistence.*;
import javax.websocket.OnMessage;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "spring_user")
public class User implements Serializable {
    @Id
    @SequenceGenerator(name = "users_sequence", sequenceName = "users_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence")
    private Long id;

    @NotBlank(message = "Nome obrigatorio")
    @Size(min = 6, message = "nome deve ter pelo menos 6 caracteres")
    private String name;

    @NotBlank(message = "Email obrigatorio")
    @Email(message = "Email não é valido", regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @NotBlank(message = "Senha obrigatoria")
    @Size(min = 8, message = "Senha deve ter ao menos 8 digitos")
    private String password;

    @NotBlank(message = "A senha de confirmação deve ser igual")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 digitos")
    private String confirm_password;

    private String avatar = "user_default.jpg";

    private String token;
    private String created_at;
    private String updated_at;

    @Enumerated(EnumType.STRING)
    private UserRole role;
//
//    @ManyToMany(mappedBy = "spring_user")
//      List<Job> jobs;


//    @OneToMany(fetch = FetchType.EAGER)
//    private List<Job> posts;
//

    @PrePersist
    @PreUpdate
    private void preUpdate() {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d/MM/yyyy");

        this.created_at = LocalDate.now().format(formatterDate);
        this.updated_at = LocalDate.now().format(formatterDate);
    }

    public User() {

    }

    public User(UserUpdateDTO request) {
        this.id = request.getId();
        this.name = request.getName();
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.confirm_password = request.getConfirm_password();
        this.created_at = request.getCreated_at();
        this.updated_at = request.getUpdated_at();
    }


    public User(Long id, @NotBlank(message = "Nome obrigatorio") @Size(min = 6, max = 30, message = "nome deve ter entre 6 e 30 caracteres") String name, @NotBlank(message = "Email obrigatorio") @Email(message = "Email não é valido", regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$") String email, @NotBlank(message = "Senha obrigatoria") @Size(min = 8, message = "Senha deve ter ao menos 8 digitos") String password, @NotBlank(message = "A senha de confirmação deve ser igual") @Size(min = 8, message = "A senha deve ter pelo menos 8 digitos") String confirm_password, String token, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
        this.token = token;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public User(@NotBlank(message = "Nome obrigatorio") @Size(min = 6, max = 30, message = "nome deve ter entre 6 e 30 caracteres") String name, @NotBlank(message = "Email obrigatorio") @Email(message = "Email não é valido", regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$") String email, @NotBlank(message = "Senha obrigatoria") @Size(min = 8, message = "Senha deve ter ao menos 8 digitos") String password, @NotBlank(message = "A senha de confirmação deve ser igual") @Size(min = 8, message = "A senha deve ter pelo menos 8 digitos") String confirm_password, String token) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
        this.token = token;
    }
}
