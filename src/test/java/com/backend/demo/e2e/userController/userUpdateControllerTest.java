package com.backend.demo.e2e.userController;

import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.controller.userController.UserFindController;
import com.backend.demo.enums.UserRole;
import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.service.user.UserCreateService;
import com.backend.demo.utils.UserFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
@DisplayName("E2E - userUpdateController")
public class userUpdateControllerTest {
    private static final String URL_UPDATE = "/api/v1/users/update";

    @Autowired
    private UserCreateService userCreateService;

    @Autowired
    private AuthService authService;


    @Autowired
    private UserFindController userFindController;

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        this.userRepository.deleteAll();
     }


    @Test
    @DisplayName("should update user")
    void shouldUpdateUser() throws Exception {
        var userCreated = new UserFactory().createWithRoleUser("33@gmail.com");

        this.userCreateService.create(userCreated);

        var login = new LoginDTO();

        login.setEmail(userCreated.getEmail());
        login.setPassword("senhasenha");

        var userAuth = this.authService.signIn(login);

        var userUpdateDTO = new UserFactory().updateUser(userCreated.getRole(), userCreated.getId(), userAuth.getBody().getToken());

        var result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/users/update")
                .content(asJsonString(userUpdateDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();


        var bodyResponse = result.getResponse().getContentAsString();

        Gson gson = new Gson();

        var MappingUser = gson.fromJson(bodyResponse, User.class);

        Assertions.assertEquals(MappingUser.getEmail(), userUpdateDTO.getEmail());

    }



    @Test
    @DisplayName("should fail in update with wrong values")
    void shouldFailInUpdateUser() throws Exception {
        var userCreated = new UserFactory().createWithRoleUser("3a3@gmail.com");

        this.userCreateService.create(userCreated);

        var login = new LoginDTO();

        login.setEmail(userCreated.getEmail());
        login.setPassword("senhasenha");

        var userAuth = this.authService.signIn(login);

        var userUpdateDTO = new UserFactory().createWithWrongValues();
        userUpdateDTO.setToken(userAuth.getBody().getToken());

        var result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/users/update")
                .content(asJsonString(userUpdateDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

