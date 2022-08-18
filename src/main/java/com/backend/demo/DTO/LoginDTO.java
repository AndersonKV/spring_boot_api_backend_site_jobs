package com.backend.demo.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginDTO {
    private Long id;

    @NotBlank(message = "Email obrigatorio")
    @Email(message = "Email não é valido", regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @NotBlank(message = "Senha obrigatoria")
    @Size(min = 8, message = "Senha deve ter ao menos 8 digitos")
    private String password;

    private String token;
    private String name;

    public LoginDTO() {

    }

    public LoginDTO(@NotBlank(message = "Email obrigatorio") @Email(message = "Email não é valido", regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$") String email, @NotBlank(message = "Senha obrigatoria") @Size(min = 8, message = "Senha deve ter ao menos 8 digitos") String password) {
        this.email = email;
        this.password = password;
    }

    public LoginDTO(Long id, String name, String email, String password, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.token = token;

    }
}
