package cj.esanar.service.implement.security;

import cj.esanar.persistence.entity.auth.ERole;
import cj.esanar.persistence.entity.auth.RoleEntity;
import cj.esanar.persistence.entity.auth.UserEntity;
import cj.esanar.persistence.repository.RoleRepository;
import cj.esanar.persistence.repository.UserRepository;
import cj.esanar.service.dtos.in.auth.AuthLoginRequest;
import cj.esanar.service.dtos.in.auth.AuthResponse;
import cj.esanar.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static cj.esanar.dataProviders.UserDetailsDataProvider.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserDetailServiceImpl userDetailServiceImpl;

    @DisplayName("Test para cargar usuario por su username")
    @Test
    void testLoadUserByUsername() {

        String username= "jeffer";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userAdmin()));

        UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(username);

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(username);
        assertThat(userDetails.getAuthorities()).extracting(GrantedAuthority::getAuthority)
                .containsExactlyInAnyOrder("CREATE","DELETE","READ","ROLE_ADMIN","UPDATE");

    }
    @DisplayName("Test que comprueba que la excepcion de usuario no encontrado funciona")
    @Test
    void testLoadUserByUsernameNotFoundException() {
        String username= "jeffer";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThatThrownBy(()->userDetailServiceImpl.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class);
    }
    @DisplayName("Test para el metodo loginUser")
    @Test
    void testLoginUser() {

        String username= "jeffer";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userAdmin()));
        when(passwordEncoder.matches("milluh123",userAdmin().getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(any(Authentication.class))).thenReturn("fake-token");

        AuthLoginRequest authLoginRequest = new AuthLoginRequest("jeffer","milluh123");

        AuthResponse authResponse = userDetailServiceImpl.loginUser(authLoginRequest);
        assertThat(authResponse).isNotNull();
        assertThat(authResponse.username()).isEqualTo(authLoginRequest.username());
        assertThat(authResponse.jwt()).isEqualTo("fake-token");
        assertThat(authResponse.status()).isTrue();

        verify(userRepository).findByUsername(username);
        verify(passwordEncoder).matches("milluh123",userAdmin().getPassword());
        verify(jwtUtil).generateToken(any(Authentication.class));


    }
    @DisplayName("Test que prueba la excepcion de contraseña incorrecta")
    @Test
    void testLoginUserBadCredentialsException() {
        String username= "john";
        String password = "123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        AuthLoginRequest authLoginRequest = new AuthLoginRequest(username,password);

        assertThatThrownBy(()->userDetailServiceImpl.loginUser(authLoginRequest))
                .isInstanceOf(UsernameNotFoundException.class);


    }

    @Test
    void testLoginInvalidPasswordException(){
        String username= "jeffer";
        String password="juan123";

        UserEntity userEntity= userAdmin();
        userEntity.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userAdmin()));
        when(passwordEncoder.matches(password,userAdmin().getPassword())).thenReturn(false);

        AuthLoginRequest authLoginRequest = new AuthLoginRequest(username,password);
        assertThatThrownBy(()->userDetailServiceImpl.loginUser(authLoginRequest))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Invalid password");
    }
}