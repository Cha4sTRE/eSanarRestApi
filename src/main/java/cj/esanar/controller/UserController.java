package cj.esanar.controller;

import cj.esanar.service.UserService;
import cj.esanar.service.dtos.in.auth.AuthCreateUserRequest;
import cj.esanar.service.dtos.in.auth.AuthResponse;
import cj.esanar.service.dtos.out.UsersDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esanar/api/v1/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<List<UsersDto>> getAllUsers() {
        List<UsersDto> dtoList=userService.listUsers();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<UsersDto> findUserById(@PathVariable Long id) {
        UsersDto userDto=userService.getUserById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<UsersDto> findUserById(@PathVariable String username) {
        UsersDto userDto=userService.getUserByUsername(username);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @PutMapping("/update/user/{id}")
    public ResponseEntity<AuthResponse> updateUser(@RequestBody @Valid AuthCreateUserRequest updateUser,
                                                   @PathVariable Long id) {
        AuthResponse authResponse= userService.updateUser(updateUser,id);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

}
