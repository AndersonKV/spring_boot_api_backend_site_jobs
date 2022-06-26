package com.backend.demo.controller.jobController;

import com.backend.demo.DTO.JobDTO;
import com.backend.demo.model.Job;
import com.backend.demo.service.job.JobUpdateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(path = "api/v1/jobs")
@CrossOrigin("*")
@Api(value = "API REST UPDATE JOB")
public class JobUpdateController {

    @Autowired
    private JobUpdateService jobUpdateService;

    @ApiOperation(value = "should update job")
    @PutMapping(path = "update")
    public void Update(@Valid @RequestBody Job request) {
        this.jobUpdateService.update(request);
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
