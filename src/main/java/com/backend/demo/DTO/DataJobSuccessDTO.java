package com.backend.demo.DTO;

import com.backend.demo.model.Job;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class DataJobSuccessDTO {
    private List<Job> data;
    private Boolean success = false;
    private HttpStatus http;

    public DataJobSuccessDTO(List<Job> data, Boolean success, HttpStatus http) {
        this.data = data;
        this.success = success;
        this.http = http;
    }
}
