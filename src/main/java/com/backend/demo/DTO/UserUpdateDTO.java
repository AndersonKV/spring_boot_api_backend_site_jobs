package com.backend.demo.DTO;

import com.backend.demo.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class UserUpdateDTO {

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

    public UserUpdateDTO(){

    }
    public UserUpdateDTO(Long id, @NotBlank(message = "Nome obrigatorio") @Size(min = 6, message = "nome deve ter pelo menos 6 caracteres") String name, @NotBlank(message = "Email obrigatorio") @Email(message = "Email não é valido", regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$") String email, @NotBlank(message = "Senha obrigatoria") @Size(min = 8, message = "Senha deve ter ao menos 8 digitos") String password, @NotBlank(message = "A senha de confirmação deve ser igual") @Size(min = 8, message = "A senha deve ter pelo menos 8 digitos") String confirm_password, String avatar, String token, String created_at, String updated_at, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
        this.avatar = avatar;
        this.token = token;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.role = role;
    }
}
