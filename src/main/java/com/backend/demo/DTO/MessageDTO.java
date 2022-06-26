package com.backend.demo.DTO;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor

public class MessageDTO {
    private String message = "";
}
