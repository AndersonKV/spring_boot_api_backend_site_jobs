package com.backend.demo.controller.userController;

import com.backend.demo.model.User;
import com.backend.demo.service.user.UserDeleteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping(path = "api/v1/users")
@CrossOrigin("*")
@Api(value="API REST DELETE USER")
public class UserDeleteController {
    @Autowired
    private  UserDeleteService userDeleteService;


    @ApiOperation(value="should delete user by id")
    @DeleteMapping(path = "delete_by_id")
    public void deleteUserById(@RequestParam("id") Long id) {
        this.userDeleteService.deleteUserById(id);
    }

    @ApiOperation(value="should destroyer all users")
    @DeleteMapping(path = "destroyer")
    public void destroyer() {
        this.userDeleteService.destroyer();
    }

}
