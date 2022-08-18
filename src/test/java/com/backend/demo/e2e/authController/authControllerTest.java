package com.backend.demo.e2e.authController;

import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.DTO.MatchingDTO;
import com.backend.demo.model.Job;
import com.backend.demo.model.Matching;
import com.backend.demo.model.User;
import com.backend.demo.repository.JobRepository;
import com.backend.demo.repository.MatchingRepository;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.service.auth.AuthService;
import com.backend.demo.service.job.JobCreateService;
import com.backend.demo.service.job.JobDeleteService;
import com.backend.demo.service.job.JobFindService;
import com.backend.demo.service.matching.MatchingCreateService;
import com.backend.demo.service.user.UserCreateService;
import com.backend.demo.utils.JobFactory;
import com.backend.demo.utils.LoginFactory;
import com.backend.demo.utils.UserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("E2E - authController")
public class authControllerTest {
    @Autowired
    MatchingCreateService matchingCreateService;

    @Autowired
    MatchingRepository matchingRepository;


    @Autowired
    private UserCreateService userCreateService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    AuthService authService;

    @Autowired
    JobFindService jobFindService;

    @Autowired
    JobDeleteService jobDeleteService;


    @Autowired
    JobCreateService jobCreateService;

    @MockBean
    User userShouldApply;

    @AfterEach
    void tearDown() {
        this.userRepository.deleteAll();
    }

    @Test
    @DisplayName("should sing in")
    void shouldSignIn() throws Exception {
        var userRoleCompany = new UserFactory().createUserRoleCompany("77@gmail.com");

        this.userCreateService.create(userRoleCompany);

        var loginDTO = new LoginFactory().create(userRoleCompany.getEmail(), "senhasenha");

        var result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/authenticate/sign_in")
                .content(asJsonString(loginDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();

        var bodyResponse = result.getResponse().getContentAsString();

        Gson gson = new Gson();

        var authUser = gson.fromJson(bodyResponse, User.class);

        Assertions.assertEquals(authUser.getEmail(), loginDTO.getEmail());


    }

    @Test
    @DisplayName("should fail in sing in")
    void shouldFailInSignIn() throws Exception {

        var loginDTO = new LoginFactory().create("asddsa@gamil.com", "senhasenha");

        var result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/authenticate/sign_in")
                .content(asJsonString(loginDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @Test
    @DisplayName("should auth by token")
    void shouldAuthByToken() throws Exception {

            var userRoleCompany = new UserFactory().createUserRoleCompany("7766@wgmail.com");

            this.userCreateService.create(userRoleCompany);

            var loginDTO = new LoginFactory().create(userRoleCompany.getEmail(), "senhasenha");

            var auth = this.authService.signIn(loginDTO);


            var result = mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/authenticate/sign_in_token")
                    .param("token", auth.getBody().getToken().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isAccepted())
                    .andReturn();



    }

    @DisplayName("should auth by token")
    void shouldFailInAuthByToken() throws Exception {

        var result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/authenticate/sign_in_token")
                .param("token", "651s65ds16sd51ds651ds6ds165")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }

//
//    @ApiOperation(value = "should sign in")
//    @PostMapping(path = "sign_in")
//    public ResponseEntity<LoginDTO> SignIn(@Valid @RequestBody LoginDTO request) {
//        return authService.signIn(request);
//    }
//
//    @ApiOperation(value = "should authenticate with token")
//    @PostMapping(path = "sign_in_token")
//    public ResponseEntity<User> SignInWithToken(@RequestParam("token") String token) {
//        return authService.signInWithToken(token);
//    }
//
//

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
