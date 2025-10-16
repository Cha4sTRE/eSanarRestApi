package cj.esanar.config.security;

import cj.esanar.service.dtos.exceptions.ErrorsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ErrorsDto dto = (ErrorsDto) request.getAttribute("error_dto");


        if (dto == null) {
            // Si viene del UserDetailsService u otro flujo estándar
            String message = authException.getMessage();

            if (authException.getCause() instanceof UsernameNotFoundException) {
                dto = new ErrorsDto(401, "Unauthorized", "User not found");
            } else if (message.contains("Bad credentials")) {
                dto = new ErrorsDto(401, "Unauthorized", "Invalid username or password");
            } else {
                dto = new ErrorsDto(401, "Unauthorized", "Authentication failed");
            }
        }

        new ObjectMapper().writeValue(response.getOutputStream(), dto);
    }
}
