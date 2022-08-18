package com.backend.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DeleteUserByIdDTO {
    @NotNull( message = "id não pode estar vazio")
    private Long id;
}
