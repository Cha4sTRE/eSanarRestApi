package cj.esanar.controller;

import cj.esanar.service.dtos.in.auth.AuthCreateUserRequest;
import cj.esanar.service.dtos.in.auth.AuthLoginRequest;
import cj.esanar.service.dtos.in.auth.AuthResponse;
import cj.esanar.service.implement.security.UserDetailServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/esanar/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserDetailServiceImpl userDetailService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest loginRequest) {
        return new ResponseEntity<>(userDetailService.loginUser(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/sign")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest createUserRequest) {
        return new ResponseEntity<>(userDetailService.createUser(createUserRequest), HttpStatus.CREATED);
    }


}
