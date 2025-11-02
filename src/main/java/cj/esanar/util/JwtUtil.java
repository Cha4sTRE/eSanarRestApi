package cj.esanar.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${spring.security.jwt.private.key}")
    private String secretKey;
    @Value("${spring.security.user.generator}")
    private String userGenerator;

    public String generateToken(Authentication authentication){
        Algorithm algorithm= Algorithm.HMAC256(secretKey);
        String username=authentication.getName();
        String permisos= authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        return JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("permisos", permisos)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+10800000))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);
    }
    public DecodedJWT veriffyToken(String token) throws JWTVerificationException {
        try{

            Algorithm algorithm=Algorithm.HMAC256(secretKey);
            JWTVerifier verifier=JWT.require(algorithm).withIssuer(this.userGenerator).build();
            return verifier.verify(token);

        }catch (JWTVerificationException e){
            throw new JWTVerificationException(e.getMessage());
        }
    }
    public String getUsernameFromToken(DecodedJWT decodedJWT){
        return decodedJWT.getSubject();
    }
    public Claim getSpecificClaim(DecodedJWT decodedJWT,String claimName){
        return decodedJWT.getClaim(claimName);
    }
    public Map<String,Claim> getClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }


}
