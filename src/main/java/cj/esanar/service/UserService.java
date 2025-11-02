package cj.esanar.service;

import cj.esanar.service.dtos.in.auth.AuthCreateUserRequest;
import cj.esanar.service.dtos.in.auth.AuthResponse;
import cj.esanar.service.dtos.out.UsersDto;

import java.util.List;

public interface UserService {

    List<UsersDto> listUsers();
    UsersDto getUserById(Long id);
    UsersDto getUserByUsername(String username);
    AuthResponse updateUser(AuthCreateUserRequest authCreateUserRequest, long id);

}
