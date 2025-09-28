package cj.esanar.config;

import cj.esanar.persistence.entity.auth.ERole;
import cj.esanar.persistence.entity.auth.PermissionEntity;
import cj.esanar.persistence.entity.auth.RoleEntity;
import cj.esanar.persistence.entity.auth.UserEntity;
import cj.esanar.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CommandInit {

    @Bean
    CommandLineRunner init(PasswordEncoder passwordEncoder, UserRepository userRepository){
        return args -> {
            PermissionEntity created= PermissionEntity.builder().name("CREATE").build();
            PermissionEntity read= PermissionEntity.builder().name("READ").build();
            PermissionEntity update= PermissionEntity.builder().name("UPDATE").build();
            PermissionEntity delete= PermissionEntity.builder().name("DELETE").build();

            RoleEntity admin= RoleEntity.builder()
                    .name(ERole.ADMIN)
                    .listaPermisos(Set.of(created,read,update,delete))
                    .build();
            RoleEntity medic= RoleEntity.builder()
                    .name(ERole.MEDIC)
                    .listaPermisos(Set.of(created,read,update))
                    .build();
            RoleEntity enf= RoleEntity.builder()
                    .name(ERole.ENF)
                    .listaPermisos(Set.of(created,read))
                    .build();

            UserEntity user= UserEntity.builder()
                    .name("Jefferson")
                    .lastName("Chaustre")
                    .username("jeffer")
                    .identifier(1092524589L)
                    .phoneNumber(3166846822L)
                    .email("chaustrejefferson@gmail.com")
                    .password(passwordEncoder.encode("milluh123"))
                    .roles(Set.of(admin,medic,enf))
                    .isEnabled(true)
                    .isAccountNonExpired(true)
                    .isCredentialsNonExpired(true)
                    .isAccountNonLocked(true)
                    .build();

            userRepository.save(user);
        };
    }

}
