package com.backend.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MatchingDTO {
    private Long id;

    private Long id_user;

    private Long id_job;
}
