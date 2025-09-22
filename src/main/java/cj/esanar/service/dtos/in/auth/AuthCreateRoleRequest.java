package cj.esanar.service.dtos.in.auth;

import jakarta.validation.constraints.Size;

import java.util.List;

/*
* dto para la asignacion de roles al momento de
* registrar un nuevo usuario en el endPoint Post
* */
public record AuthCreateRoleRequest(@Size(max = 3,message = "Limite Alcanzado") List<String> roleList) {
}
