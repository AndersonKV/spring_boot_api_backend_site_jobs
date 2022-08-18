package com.backend.demo.integration.authService;

import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.enums.UserRole;
import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.service.user.UserCreateService;
import com.backend.demo.service.user.UserDeleteService;
import com.backend.demo.utils.UserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import org.junit.Test;

import java.awt.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@DisplayName("TEST INTEGRATION - AuthServiceTest")
public class AuthServiceTest {
    private final String EMAIL_NOT_FOUND = "esse email não foi registrado";
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;
    @Autowired
    UserDeleteService userDeleteService;
    @Autowired
    UserCreateService userCreateService;

    @AfterEach
    void tearDown() {
        this.userRepository.deleteAll();
    }


    @Test
    @DisplayName("should sign in")
    public final void shouldAuthenticate() {
        var userFactory = new UserFactory().createWithRoleUser("exeqqqmplwwwo1@gmail.com");

        this.userCreateService.create(userFactory);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userFactory.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);

        Assertions.assertEquals(HttpStatus.ACCEPTED, sign_in.getStatusCode());
    }

    @Test
    @DisplayName("should authenticate with token")
    public final void shouldAuthenticateWithToken() {
        var userFactory = new UserFactory().createWithRoleUser("exemplo1@gmail.com");

        this.userCreateService.create(userFactory);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userFactory.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);
        ResponseEntity<User> authToken = this.authService.signInWithToken(sign_in.getBody().getToken());

        Assertions.assertEquals(HttpStatus.ACCEPTED, authToken.getStatusCode());
        Assertions.assertEquals(sign_in.getBody().getId(), authToken.getBody().getId());
    }



    @Test
    @DisplayName("should fail in authenticate")
    public final void shouldFailInAuthenticate() {
        try {
            LoginDTO loginDTO = new LoginDTO();

            loginDTO.setEmail("dsadsdsaads@gmial.com");
            loginDTO.setPassword("exemploexemplo");

            this.authService.signIn(loginDTO);
        } catch (RuntimeException e) {
            Assertions.assertEquals(EMAIL_NOT_FOUND, e.getMessage());
        }

    }
    @Test
    @DisplayName("should fail in sign in with wrong password")
    public final void shouldFailWithWrongPassword() {
        try {
            var userFactory = new UserFactory().createWithRoleUser("exemp33lo1@gmail.com");

            this.userCreateService.create(userFactory);

            LoginDTO loginDTO = new LoginDTO();

            loginDTO.setEmail(userFactory.getEmail());
            loginDTO.setPassword("senhasenhasss");

            this.authService.signIn(loginDTO);
        }  catch (RuntimeException e) {
            Assertions.assertTrue(e.getMessage().contains("Senha errada"));
        }
    }

    @Test
    @DisplayName("should fail with wrong token")
    public final void shouldFailInAuthenticateWithToken() {
        try {
            String wrongToken = "kiOiIxIiwiaWF0IjoxNjU2NTA5Mjc2LCJzdWIiOiIkMmEkMYbjZHTXJQWnJGdWIwNHQxRTFCNGdZZHZYdU9IV2UiLCJpc3MiOiJhbmRlcnNvbiIsImV4cCI6MTY1NjUwOTg3Nn0.W5YV5IX9YPgaGM7klcJmqX_6jLIRH9SQDAcKti8_vDc";
            this.authService.signInWithToken(wrongToken);
        }  catch (RuntimeException e) {
            Assertions.assertTrue(e.getMessage().contains("JWT"));
        }
    }
}
