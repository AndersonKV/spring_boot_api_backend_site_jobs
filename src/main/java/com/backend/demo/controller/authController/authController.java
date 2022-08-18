package com.backend.demo.controller.authController;

import com.backend.demo.DTO.LoginDTO;
import com.backend.demo.model.User;
import com.backend.demo.service.auth.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "api/v1/authenticate", produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin("*")
@Api(value = "API REST CREATE USER")

public class authController {
    @Autowired
    private AuthService authService;

    @ApiOperation(value = "should sign in")
    @PostMapping(path = "sign_in")
    public   ResponseEntity<LoginDTO> SignIn(@Valid @RequestBody LoginDTO request) {
        return authService.signIn(request);
    }

    @ApiOperation(value = "should authenticate with token")
    @GetMapping(path = "sign_in_token")
    public ResponseEntity<User> SignInWithToken(@RequestParam("token") String token) {
        return authService.signInWithToken(token);
    }

}
