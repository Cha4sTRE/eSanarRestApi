package cj.esanar.service.dtos.in.auth;

public record AuthResponse(
        String username,
        String message,
        String jwt,
        boolean status
){
}
