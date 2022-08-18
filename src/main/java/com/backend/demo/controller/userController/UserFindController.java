package com.backend.demo.controller.userController;

import com.backend.demo.model.User;
import com.backend.demo.service.user.UserFindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
@CrossOrigin("*")
@Api(value = "API REST FIND USER")
public class UserFindController {
    @Autowired
    private UserFindService userFindService;

    @ApiOperation(value = "should find user by id")
    @GetMapping(path = "find_by_id")
    public ResponseEntity GetUserById(@RequestParam("id") Long id) {
        return this.userFindService.getUserById(id);
    }

    @ApiOperation(value = "should list all users")
    @GetMapping(path = "list_users")
    public ResponseEntity<List<User>> ListUsers() {
        return this.userFindService.list_users();
    }


}
