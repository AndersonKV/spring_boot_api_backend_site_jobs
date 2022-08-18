package com.backend.demo.integration.userService;

import com.backend.demo.DemoApplicationTests;
import com.backend.demo.enums.UserRole;
import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.user.UserCreateService;
import com.backend.demo.utils.UserFactory;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
@RunWith(SpringRunner.class)
@DisplayName("TEST INTEGRATION - UserCreateService")
public class UserCreateServiceTest   {
    @MockBean
    UserRepository userRepository;

    @Autowired
    UserCreateService userCreateService;


    @AfterEach
    void tearDown() {
        this.userRepository.deleteAll();
    }


    @Test
    @DisplayName("should create user")
    public final void create() {
        var userCreated = new UserFactory().createWithRoleUser("exemplo1@gmail.com");

        ResponseEntity response = this.userCreateService.create(userCreated);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    @DisplayName("should fail in create with email in use")
    public void shouldFailInCreateUserWithSameEmail() {

        try {
            var userCreated = new UserFactory().createWithRoleUser("exemplo1@gmail.com");

            this.userCreateService.create(userCreated);
            this.userCreateService.create(userCreated);

        } catch (RuntimeException e) {
            Assertions.assertEquals("email já foi registrado", e.getMessage());
        }

    }

    @Test
    @DisplayName("should fail with wrong password")
    public void shouldFailInCreateUserWithWrongConfirmPassword() {

        try {
            var userCreated = new UserFactory().createWithWrongPassword("exemplo1@gmail.com");

            this.userCreateService.create(userCreated);

        } catch (RuntimeException e) {
            Assertions.assertEquals("Senha de confirmação deve ser igual", e.getMessage());
        }

    }


    @Test
    @DisplayName("should fail in create with empty role")
    public void shouldFailInCreateUserEmptyRole() {

        try {
            var userCreated = new UserFactory().createWithWrongCompany();

            this.userCreateService.create(userCreated);

        } catch (RuntimeException e) {
            Assertions.assertEquals("Necessário escolher entre user e company", e.getMessage());
        }

    }



}

