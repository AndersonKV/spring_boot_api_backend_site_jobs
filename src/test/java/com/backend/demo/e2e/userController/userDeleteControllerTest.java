package com.backend.demo.e2e.userController;

import com.backend.demo.enums.UserRole;
import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.user.UserCreateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("E2E - userDeleteController")
public class userDeleteControllerTest {
    private static final String URL_DELETE_BY_ID = "/api/v1/users/delete_by_id/";
    private static final String URL_DESTROYER = "/api/v1/users/destroyer";

    @Autowired
    private UserCreateService userCreateService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private MockMvc mockMvc;

    private User userToBeDeleted;

    @Test
    @DisplayName("should delete user by id")
    void shouldDeleteUserById() throws Exception {
        User user = new User();

        user.setName("anderson");
        user.setEmail("anderson@gmail.com");
        user.setConfirm_password("123456789");
        user.setPassword("123456789");
        user.setRole(UserRole.user);

        this.userCreateService.create(user);

        var userCreated = this.userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new IllegalStateException("a"));

        mockMvc.perform(MockMvcRequestBuilders
                .delete(URL_DELETE_BY_ID + userCreated.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

    }


    @Test
    @DisplayName("should fail in delete user by")
    void shouldFailInDeleteUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(URL_DELETE_BY_ID + 99l)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should destroyer all users")
    void shouldDestroyerAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(URL_DESTROYER)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
