package com.backend.demo.controller.userController;

import com.backend.demo.service.user.UserDeleteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(path = "api/v1/users")
@CrossOrigin("*")
@Api(value = "API REST DELETE USER")
public class UserDeleteController {
    @Autowired
    private UserDeleteService userDeleteService;

    @ApiOperation(value = "should delete user by id")
    @DeleteMapping("delete_by_id/{id}")
    public ResponseEntity Delete_by_id(@PathVariable Long id) {
        return this.userDeleteService.delete_by_id(id);
    }


    @ApiOperation(value = "should delete all users")
    @DeleteMapping("destroyer")
    public ResponseEntity Destroyer( ) {
        return this.userDeleteService.deleteAll();
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
