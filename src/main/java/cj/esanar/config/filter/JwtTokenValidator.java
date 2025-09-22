package cj.esanar.config.filter;

import cj.esanar.util.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token= request.getHeader(HttpHeaders.AUTHORIZATION);
        if(token!=null){
            //extrae token de header http
            token= token.substring(7);

            //decodifica el token
            DecodedJWT decode= this.jwtUtil.veriffyToken(token);
            //obtenemos el usuaario del token decodificado
            String username= this.jwtUtil.getUsernameFromToken(decode);
            //obtenemos el string de permisos
            String authoritiesList= this.jwtUtil.getSpecificClaim(decode,"permisos").asString();
            //convertimos el string de permisos en una lista de grantedAuthority
            Collection<? extends GrantedAuthority> authorities= AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesList);

            SecurityContext context = SecurityContextHolder.getContext();
            //creamos un objeto de autenticacion para tokens con lo extraido de él
            Authentication authentication=new UsernamePasswordAuthenticationToken(username,null,authorities);
            //lo añadimos al contexto de spring security
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }
        filterChain.doFilter(request, response);
    }
}
