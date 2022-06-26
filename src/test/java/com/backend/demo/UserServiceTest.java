package com.backend.demo;

import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.support.NullValue;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@DataJpaTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    //final Long expected = Mockito.mock(Long.class);

    @Autowired
    public UserRepository userRepository;


    @AfterEach
    void tearDown() {
        this.userRepository.deleteAll();
    }


    @Test
    @DisplayName("should create user")
    public final void create() {
        User user = new User("anderson", "anwewemail.com", "123456789", "123456789");
        User createdUser = this.userRepository.save(user);

        // Mockito.when(createdUser.getId()).thenReturn(expected);
        Assertions.assertEquals(user.getEmail(), createdUser.getEmail());
    }


    @Test
    @DisplayName("should fail in get email")
    public void shouldFailInGetEmail() {
        Optional<User> user = this.userRepository.findByEmail("sadsasadasd");
        Assertions.assertEquals(Boolean.FALSE, user.isPresent());
    }


    @Test
    @DisplayName("should get all users")
    public void shouldGetAllUsers() {
        List<User> findAll = this.userRepository.findAll();
        Assertions.assertEquals(ArgumentMatchers.anyInt(), findAll.size());
    }

}

