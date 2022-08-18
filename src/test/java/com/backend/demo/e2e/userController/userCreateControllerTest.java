package com.backend.demo.e2e.userController;


import com.backend.demo.enums.UserRole;
import com.backend.demo.model.User;
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
@DisplayName("E2E - userCreateController")
public class userCreateControllerTest   {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjYwNzQ2NzQwLCJzdWIiOiIkMmEkMTAkRFFYRmE5Si9BY0NFN1ZJd3M5TlRGZUxRZTh6MzBVeGFMdVZQTFVtWnJuZXE3Qmdrd2VPUEMiLCJpc3MiOiJhbmRlcnNvbiIsImV4cCI6MTY2MDc0NzM0MH0.jbWvvJTyKATbAHyIxu7Kju7De-8F-QJUj4iSkcyG9sa";
    @Autowired
    private UserCreateService userCreateService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("shoud create user")
    void shouldCreateUser() throws Exception {

        User user = new User();

        user.setName("anderson");
        user.setEmail("anderson@gmail.com");
        user.setConfirm_password("123456789");
        user.setPassword("123456789");
        user.setRole(UserRole.user);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/users/create")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }

    @Test
    @DisplayName("shoud fail in create user with wrong email")
    void shouldFailInCreateUserWithWrongEmail() throws Exception {

        User user = new User();

        user.setName("anderson");
        user.setEmail("malformatedemail");
        user.setConfirm_password("123456789");
        user.setPassword("123456789");
        user.setRole(UserRole.user);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/users/create")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @Test
    @DisplayName("shoud fail in create user with wrong password")
    void shouldFailInCreateUserWithWrongPassword() throws Exception {

        User user = new User();

        user.setName("anderson");
        user.setEmail("sasaddadas@gmial.com");
        user.setConfirm_password("123456789");
        user.setPassword("123456w789");
        user.setRole(UserRole.user);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/users/create")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @Test
    @DisplayName("shoud fail in create user with wrong role")
    void shouldFailInCreateUserWithWrongRole() throws Exception {

        User user = new User();

        user.setName("anderson");
        user.setEmail("sasaddadaswwww@gmial.com");
        user.setConfirm_password("123456789");
        user.setPassword("123456789");


        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/users/create")
                .content(asJsonString(user))
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

