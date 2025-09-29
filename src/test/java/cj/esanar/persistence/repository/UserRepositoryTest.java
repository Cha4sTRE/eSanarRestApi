package cj.esanar.persistence.repository;

import cj.esanar.persistence.entity.auth.ERole;
import cj.esanar.persistence.entity.auth.PermissionEntity;
import cj.esanar.persistence.entity.auth.RoleEntity;
import cj.esanar.persistence.entity.auth.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import java.util.Set;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private PermissionEntity create,read,update,delete;
    private RoleEntity admin,enf,medic,visitor;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        create= PermissionEntity.builder().name("CREATE").build();
        read= PermissionEntity.builder().name("READ").build();
        update= PermissionEntity.builder().name("UPDATE").build();
        delete= PermissionEntity.builder().name("DELETE").build();
        admin = RoleEntity.builder().name(ERole.ADMIN).listaPermisos(Set.of(read,create,update,delete)).build();
        medic = RoleEntity.builder().name(ERole.MEDIC).listaPermisos(Set.of(read,create,update)).build();
        enf = RoleEntity.builder().name(ERole.ENF).listaPermisos(Set.of(read,create)).build();
        visitor = RoleEntity.builder().name(ERole.VISITOR).listaPermisos(Set.of(read)).build();
        user= UserEntity.builder()
                .name("Jefferson")
                .lastName("Chaustre")
                .username("jeffer")
                .identifier(1092524589L)
                .phoneNumber(3166846822L)
                .email("chaustrejefferson@gmail.com")
                .password("milluh123")
                .roles(Set.of(admin,medic,enf,visitor))
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isAccountNonLocked(true)
                .build();
    }

    @Test
    void saveUser(){
        UserEntity userTest= userRepository.save(user);
        assertThat(userTest).isNotNull();
        assertThat(userTest.getRoles()).isNotEmpty();
        assertThat(userTest.getId()).isGreaterThan(0);
        assertThat(userTest.getRoles()).isInstanceOf(Set.class);
        assertThat(userTest.getRoles().size()).isEqualTo(4);
        assertThat(userTest.getRoles().contains(admin)).isTrue();
    }
    @Test
    void testUserFindByUsername(){
        UserEntity userTest= userRepository.save(user);
        Optional<UserEntity> userNameTest= userRepository.findByUsername(userTest.getUsername());

        assertThat(userNameTest.isPresent()).isTrue();
        assertThat(userNameTest.get().getUsername()).isEqualTo(userTest.getUsername());
    }
}