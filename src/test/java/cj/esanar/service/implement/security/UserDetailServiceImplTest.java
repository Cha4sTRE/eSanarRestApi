package cj.esanar.service.implement.security;

import cj.esanar.persistence.repository.RoleRepository;
import cj.esanar.persistence.repository.UserRepository;
import cj.esanar.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static cj.esanar.dataProviders.UserDetailsDataProvider.*;
import static org.assertj.core.api.Assertions.*;
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

    @Test
    void loginUser() {
    }

    @Test
    void createUser() {
    }
}