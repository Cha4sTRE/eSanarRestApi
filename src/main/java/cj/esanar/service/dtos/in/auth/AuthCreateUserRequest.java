package cj.esanar.service.dtos.in.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/*
* dto para crear un nuevo usuario en el registro con el metodo Post
* */
public record AuthCreateUserRequest(
        @NotBlank String name,
        @NotBlank String lastName,
        @NotBlank @Email String email,
        @Positive Long identifier,
        @Positive Long phone,
        @NotBlank String username,
        @NotBlank String password,
        @Valid AuthCreateRoleRequest roleRequest
){}
