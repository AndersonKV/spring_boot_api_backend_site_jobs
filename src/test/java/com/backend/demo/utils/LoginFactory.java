package com.backend.demo.utils;

import com.backend.demo.DTO.LoginDTO;

public class LoginFactory {

    public LoginDTO create(String email, String password) {
        return new LoginDTO(email, password);
    }
}
