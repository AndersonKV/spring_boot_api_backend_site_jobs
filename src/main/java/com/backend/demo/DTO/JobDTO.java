package com.backend.demo.DTO;

import com.backend.demo.model.Job;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class JobDTO extends Job {
    @NotBlank(message = "token obrigatorio")
    private String token;
}
