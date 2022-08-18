package com.backend.demo.integration.userService;

import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.DTO.UserDTO;
import com.backend.demo.DemoApplicationTests;
import com.backend.demo.enums.UserRole;
import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.service.user.UserCreateService;
import com.backend.demo.service.user.UserFindService;
import com.backend.demo.utils.UserFactory;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@DisplayName("TEST INTEGRATION - UserFindService")
public class UserFindServiceTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCreateService userCreateService;

    @Autowired
    UserFindService userFindService;

    @Autowired
    AuthService authService;

    @AfterEach
    void tearDown() {
        this.userRepository.deleteAll();
    }


    @Test
    @DisplayName("should find user by id")
    public final void shouldFindById() {
        var userCreated = new UserFactory().createWithRoleUser("exemplo1333@gmail.com");

        this.userCreateService.create(userCreated);

        LoginDTO login = new LoginDTO();

        login.setEmail(userCreated.getEmail());
        login.setPassword("senhasenha");

        var userAuth = this.authService.signIn(login);

        Assertions.assertEquals(HttpStatus.ACCEPTED, userAuth.getStatusCode());
    }


    @Test
    @DisplayName("should fail in get user by id")
    public final void shouldFailInGetById() {
        try {
            this.userFindService.getUserById(99l);
        } catch (RuntimeException e) {
            Assertions.assertEquals("id não encontrado", e.getMessage());
        }
    }


    @Test
    @DisplayName("should get all users")
    public final void shouldGetAllUsers() {
        ResponseEntity<List<User>> findAll = this.userFindService.list_users();
        Assertions.assertEquals(HttpStatus.ACCEPTED, findAll.getStatusCode());
        Assertions.assertTrue(findAll.getBody().size() >= 0);
    }
}
