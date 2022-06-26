package com.backend.demo.request;


import com.sun.istack.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@NonNull

public class UserRequest {
    @NotNull
    private final String email;
    @NotNull
    private final String password;
    @NotNull
    private final String confirmPassword;
    @NotNull
    private final String name;

}
