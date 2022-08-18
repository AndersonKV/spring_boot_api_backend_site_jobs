package com.backend.demo.controller.jobController;

import com.backend.demo.model.Job;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.backend.demo.service.job.JobCreateService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/jobs")
@CrossOrigin(origins = "*")
@Api(value="API REST CREATE JOB")
public class JobCreateController {
    @Autowired
    private JobCreateService jobCreateService;

    @ApiOperation(value="should create job")
    @PostMapping(path = "create")
    public ResponseEntity  Create(@Valid @RequestBody Job request) {
        return this.jobCreateService.create(request);
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
