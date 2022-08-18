package com.backend.demo.controller.userController;

import com.backend.demo.DTO.UserUpdateDTO;
import com.backend.demo.model.User;
import com.backend.demo.service.user.UserUpdateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/users")
@CrossOrigin("*")
@Api(value="API REST UPDATE USER")
public class UserUpdateController {
    @Autowired
    private UserUpdateService userUpdateService;

    @ApiOperation(value="should update user")
    @PostMapping(path = "update")
    public ResponseEntity<User> Update(@Valid @RequestBody UserUpdateDTO request) {
        return this.userUpdateService.update(request);
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
