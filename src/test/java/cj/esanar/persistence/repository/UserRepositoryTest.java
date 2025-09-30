package cj.esanar.persistence.repository;

import cj.esanar.persistence.entity.auth.ERole;
import cj.esanar.persistence.entity.auth.PermissionEntity;
import cj.esanar.persistence.entity.auth.RoleEntity;
import cj.esanar.persistence.entity.auth.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
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
    @DisplayName("Test para guardar usuario")
    @Test
    void testSaveUser(){
        UserEntity userTest= userRepository.save(user);
        assertThat(userTest).isNotNull();
        assertThat(userTest.getRoles()).isNotEmpty();
        assertThat(userTest.getId()).isGreaterThan(0);
        assertThat(userTest.getRoles()).isInstanceOf(Set.class);
        assertThat(userTest.getRoles().size()).isEqualTo(4);
        assertThat(userTest.isEnabled()).isTrue();
        assertThat(userTest.isAccountNonExpired()).isTrue();
        assertThat(userTest.isCredentialsNonExpired()).isTrue();
        assertThat(userTest.isAccountNonLocked()).isTrue();
    }
    @Test
    @DisplayName("Test para buscar usuario por su username")
    void testUserFindByUsername(){
        UserEntity userTest= userRepository.save(user);
        Optional<UserEntity> userNameTest= userRepository.findByUsername(userTest.getUsername());

        assertThat(userNameTest.isPresent()).isTrue();
        assertThat(userNameTest.get().getUsername()).isEqualTo(userTest.getUsername());
        assertThat(userNameTest.get().isEnabled()).isTrue();
        assertThat(userNameTest.get().isAccountNonExpired()).isTrue();
        assertThat(userNameTest.get().isCredentialsNonExpired()).isTrue();
        assertThat(userNameTest.get().isAccountNonLocked()).isTrue();
    }
    @Test
    @DisplayName("Test para buscar todos los usuarios")
    void testUserFindAll(){
        UserEntity user2= UserEntity.builder()
                .name("Angelica")
                .lastName("Gaitan")
                .username("angelica")
                .identifier(105984526L)
                .phoneNumber(355684521L)
                .email("angelica@gmail.com")
                .password("camila123")
                .roles(Set.of(medic))
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isAccountNonLocked(true)
                .build();
        UserEntity user3= UserEntity.builder()
                .name("Wilmer")
                .lastName("Gelvez")
                .username("wilmer")
                .identifier(1085495216L)
                .phoneNumber(35489658L)
                .email("wilmer@gmail.com")
                .password("xd123")
                .roles(Set.of(visitor))
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isAccountNonLocked(true)
                .build();

        userRepository.saveAll(List.of(user,user2,user3));
        List<UserEntity> testListUsers= (List<UserEntity>) userRepository.findAll();
        assertThat(testListUsers).isNotEmpty();
        assertThat(testListUsers.size()).isEqualTo(3);
        assertThat(testListUsers).isInstanceOf(List.class);
        assertThat(testListUsers.get(0).getName()).isEqualTo(this.user.getName());
    }

    @DisplayName("Test para eliminar usuario")
    @Test
    void testDeleteUser(){

        UserEntity userTest= userRepository.save(user);
        userRepository.delete(userTest);

        Optional<UserEntity> userNameTest= userRepository.findByUsername(userTest.getUsername());
        assertThat(userNameTest.isPresent()).isFalse();

    }

}