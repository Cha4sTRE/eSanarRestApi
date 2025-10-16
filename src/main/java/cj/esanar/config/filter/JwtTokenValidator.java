package cj.esanar.config.filter;

import cj.esanar.config.security.AuthEntryPoint;
import cj.esanar.service.dtos.exceptions.ErrorsDto;
import cj.esanar.util.JwtUtil;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
public class JwtTokenValidator extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthEntryPoint authEntryPoint;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);

                DecodedJWT decodedJWT = jwtUtil.veriffyToken(token);
                String username = jwtUtil.getUsernameFromToken(decodedJWT);
                String authorities = jwtUtil.getSpecificClaim(decodedJWT, "permisos").asString();

                Authentication auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities)
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            filterChain.doFilter(request, response);

        } catch (JWTVerificationException e) {
            // 🔹 Creamos un DTO de error y lo guardamos como atributo para el EntryPoint
            ErrorsDto dto = new ErrorsDto(401,"Unauthorized", "Invalid or expired JWT token");
            request.setAttribute("error_dto", dto);

            // 🔹 Delegamos al EntryPoint (ya configurado en el SecurityFilterChain)
            authEntryPoint.commence(request, response,
                    new AuthenticationServiceException("Invalid JWT token", e));
        }
    }
}
