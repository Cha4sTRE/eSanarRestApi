package cj.esanar.persistence.repository;

import cj.esanar.persistence.entity.auth.ERole;
import cj.esanar.persistence.entity.auth.PermissionEntity;
import cj.esanar.persistence.entity.auth.RoleEntity;
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
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private PermissionEntity read,create,update,delete;
    private RoleEntity admin,enf,medic,visitor;

    @BeforeEach
    void setUp() {
        create= PermissionEntity.builder().name("CREATE").build();
        read= PermissionEntity.builder().name("READ").build();
        update= PermissionEntity.builder().name("UPDATE").build();
        delete= PermissionEntity.builder().name("DELETE").build();
        admin = RoleEntity.builder()
                .name(ERole.ADMIN)
                .listaPermisos(Set.of(read,create,update,delete))
                .build();
        medic = RoleEntity.builder()
                .name(ERole.MEDIC)
                .listaPermisos(Set.of(read,create,update))
                .build();
        enf = RoleEntity.builder()
                .name(ERole.ADMIN)
                .listaPermisos(Set.of(read,create))
                .build();
        visitor = RoleEntity.builder()
                .name(ERole.ADMIN)
                .listaPermisos(Set.of(read))
                .build();
    }

    @DisplayName("Test guardar rol")
    @Test
    void testSaveRole(){
        RoleEntity roleEntity= roleRepository.save(admin);
        assertThat(roleEntity).isNotNull();
        assertThat(roleEntity.getName()).isEqualTo(ERole.ADMIN);
        assertThat(roleEntity.getListaPermisos().size()).isEqualTo(4);
        assertThat(roleEntity.getListaPermisos()).contains(read);
    }
    @DisplayName("Test buscar roles ")
    @Test
    void testFindAllRoles(){
        roleRepository.saveAll(List.of(admin,enf,medic,visitor));
        List<RoleEntity> roleListTest= (List<RoleEntity>) roleRepository.findAll();
        assertThat(roleListTest).isNotEmpty();
        assertThat(roleListTest.size()).isEqualTo(4);
        assertThat(roleListTest).contains(admin,enf,medic,visitor);
    }
    @DisplayName("Test buscar rol por id")
    @Test
    void testFindRoleById(){
        RoleEntity testRole= roleRepository.save(admin);
        Optional<RoleEntity> roleEntity= roleRepository.findById(testRole.getId());
        assertThat(roleEntity.isPresent()).isTrue();
        assertThat(roleEntity.get().getName()).isEqualTo(ERole.ADMIN);
    }
    @DisplayName("Test eliminar rol")
    @Test
    void testDeleteRole(){
        RoleEntity testRole= roleRepository.save(admin);
        roleRepository.delete(testRole);
        Optional<RoleEntity> roleEntity= roleRepository.findById(testRole.getId());
        assertThat(roleEntity.isPresent()).isFalse();
    }
    @Test
    void testFindRoleEntitiesByNameIn(){
        roleRepository.saveAll(List.of(admin,enf,medic,visitor));
        List<String> rolesNames= List.of("ADMIN","ENF","MEDIC","VISITOR");
        List<RoleEntity> roleEntities= roleRepository.findRoleEntitiesByNameIn(rolesNames);
        assertThat(roleEntities).isNotEmpty();
        assertThat(roleEntities.size()).isEqualTo(4);
        assertThat(roleEntities).contains(admin,enf,medic,visitor);
    }
}