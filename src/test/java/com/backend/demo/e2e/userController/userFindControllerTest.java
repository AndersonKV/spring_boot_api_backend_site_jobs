package com.backend.demo.e2e.userController;

import com.backend.demo.enums.UserRole;
import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.user.UserCreateService;
import com.backend.demo.utils.UserFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("E2E - userFindController")
public class userFindControllerTest {
    @Autowired
    private UserCreateService userCreateService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("shoud find user by id")
    void shouldFindUserById() throws Exception {
        try {
            var user = new UserFactory().createWithRoleUser("ande20322rson@gmail.com");

            this.userCreateService.create(user);

            User getUser = this.userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new IllegalStateException("error"));

            String id = String.valueOf(Long.valueOf(getUser.getId()));

            var result = mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/users/find_by_id")
                    .param("id", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isAccepted()).andReturn();

            var bodyResponse = result.getResponse().getContentAsString();

            Gson gson = new Gson();

            var MappingUser = gson.fromJson(bodyResponse, User.class);

            Assertions.assertEquals(MappingUser.getEmail(), user.getEmail());

        } catch (IllegalStateException e) {
            throw new RuntimeException(e.getMessage());

        }
    }


    @Test
    @DisplayName("shoud fail find user by id")
    void shouldFailInFindUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users/find_by_id")
                .param("id", String.valueOf(99l))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("shoud get list users")
    void shouldGetAllUsers() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users/list_users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
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
