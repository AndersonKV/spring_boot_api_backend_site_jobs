package com.backend.demo.DTO;

import lombok.Getter;
import lombok.Setter;
import org.thymeleaf.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Locale;

@Getter
@Setter
public class UserDTO {


    private Long id;

    private String name;

    private String email;

    private String password;

    private String confirmPassword;

    private String avatar;

    private String token;
    private String created_at;
    private String update_at;

    public UserDTO() {

    }
     public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

     public UserDTO(Long id, String name, String email, String avatar) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
    }


    public UserDTO(Long id, String token, String email) {
        this.id = id;
        this.token = token;
        this.email = email;
    }
}
