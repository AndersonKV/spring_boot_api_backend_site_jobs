package com.backend.demo.integration.userService;

import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.DTO.UserUpdateDTO;
import com.backend.demo.DemoApplicationTests;
import com.backend.demo.enums.UserRole;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.service.user.UserCreateService;
import com.backend.demo.service.user.UserFindService;
import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.user.UserUpdateService;
import com.backend.demo.utils.UserFactory;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@DisplayName("TEST INTEGRATION - UserUpdateService")
public class UserUpdateServiceTest   {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCreateService userCreateService;

    @Autowired
    UserFindService userFindService;


    @Autowired
    UserUpdateService userUpdateService;


    @Autowired
    AuthService authService;

    @AfterEach
    void tearDown() {
        this.userRepository.deleteAll();
    }


    @Test
    @DisplayName("should update user")
    public final void shouldUpdateUser() {
        var userCreated = new UserFactory().createWithRoleUser("exem3plo1333@gmail.com");

        this.userCreateService.create(userCreated);

        var login = new LoginDTO();

        login.setEmail(userCreated.getEmail());
        login.setPassword("senhasenha");

        var userAuth = this.authService.signIn(login);

        var userUpdateDTO = new UserFactory().updateUser(userCreated.getRole(), userCreated.getId(), userAuth.getBody().getToken());

        ResponseEntity<User> UserUpdatedFinal = this.userUpdateService.update(userUpdateDTO);
        Assertions.assertEquals(HttpStatus.CREATED, UserUpdatedFinal.getStatusCode());
    }


    @Test
    @DisplayName("should fail in update user with wrong password")
    public final void shouldFailInUpdate() {
        try {
            var userCreated = new UserFactory().createWithRoleUser("exe333m3plo1333@gmail.com");

            this.userCreateService.create(userCreated);

            var login = new LoginDTO();

            login.setEmail(userCreated.getEmail());
            login.setPassword("senhasenha");

            var userAuth = this.authService.signIn(login);

            var userUpdateDTO = new UserFactory().updateUserWithWrongPassword(userCreated.getRole(), userCreated.getId(), userAuth.getBody().getToken());

            this.userUpdateService.update(userUpdateDTO);
        } catch (RuntimeException e) {
            Assertions.assertEquals("Senha de confirmação deve ser igual", e.getMessage());
        }
    }

    @Test
    @DisplayName("should fail in update user with in use email")
    public final void shouldFailInUpdateWithEmail() {
        try {
            var userCreated = new UserFactory().createWithRoleUser("lorem@gmail.com");
            var userCreated2 = new UserFactory().createWithRoleUser("exe444m3plo1333@gmail.com");

            this.userCreateService.create(userCreated);
            this.userCreateService.create(userCreated2);

            var login = new LoginDTO();

            login.setEmail(userCreated.getEmail());
            login.setPassword("senhasenha");

            var userAuth = this.authService.signIn(login);

            var userUpdateDTO = new UserFactory().updateUserWithEmailExistent(userCreated.getId(), "lorem@gmail.com", userAuth.getBody().getToken());

            this.userUpdateService.update(userUpdateDTO);
        } catch (RuntimeException e) {
            Assertions.assertEquals("email já está em uso", e.getMessage());
        }
    }

    @Test
    @DisplayName("should fail in update user with in use email")
    public final void shouldFailInUpdateWithToken() {
        try {
            var userCreated = new UserFactory().createWithRoleUser("exem333plo1333@gmail.com");

            this.userCreateService.create(userCreated);

            var login = new LoginDTO();

            login.setEmail(userCreated.getEmail());
            login.setPassword("senhasenha");

            var userAuth = this.authService.signIn(login);

            var userUpdateDTO = new UserFactory().updateUser(
                    userCreated.getRole(),
                    userCreated.getId(),
                    userAuth.getBody().getToken() + 11);

             this.userUpdateService.update(userUpdateDTO);
        } catch (RuntimeException e) {
           Assertions.assertTrue(e.getMessage().contains("JWT"));
        }
    }

    @Test
    @DisplayName("should fail in update user with in use email")
    public final void shouldFailInUpdateWithWrongId() {
        try {
            var userCreated = new UserFactory().createWithRoleUser("ex333em3plo1333@gmail.com");

            this.userCreateService.create(userCreated);

            var login = new LoginDTO();

            login.setEmail(userCreated.getEmail());
            login.setPassword("senhasenha");

            var userAuth = this.authService.signIn(login);

            var userUpdateDTO = new UserFactory().updateUser(userCreated.getRole(), 99l, userAuth.getBody().getToken());

            ResponseEntity<User> UserUpdatedFinal = this.userUpdateService.update(userUpdateDTO);
        } catch (RuntimeException e) {
            Assertions.assertEquals(e.getMessage(), "parece ter ocorrido um problema com id diferente");
        }
    }
}
