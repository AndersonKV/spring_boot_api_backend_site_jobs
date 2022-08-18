package com.backend.demo.integration.userService;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;


import org.junit.Test;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DisplayName("TEST INTEGRATION - UserDTest")
public class UserDeleteServiceTest {
    private final String ID_NOT_FOUND = "id não encontrado";
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
    @DisplayName("should delete user by id")
    public void shouldDeleteUser() {
        var userCreated = new UserFactory().createWithRoleUser("exemplo2441@gmail.com");

        this.userCreateService.create(userCreated);

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(userCreated.getEmail());
        loginDTO.setPassword("senhasenha");

        var sign_in = this.authService.signIn(loginDTO);

        var deleted = this.userDeleteService.delete_by_id(sign_in.getBody().getId());

        Assertions.assertEquals(deleted.getStatusCode(), HttpStatus.ACCEPTED);

    }

    @Test
    @DisplayName("should fail in delete user by id")
    public void shouldFailInDeleteUser() {
        try {
            this.userDeleteService.delete_by_id(99l);
         } catch (RuntimeException e) {
            Assertions.assertEquals(ID_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    @DisplayName("should destroyer all users")
    public void shouldDestroyerAllUsers() {
        var response = this.userDeleteService.deleteAll();
        Assertions.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

    }


}
