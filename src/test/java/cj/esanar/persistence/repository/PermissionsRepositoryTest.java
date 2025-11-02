package cj.esanar.persistence.repository;

import cj.esanar.persistence.entity.auth.PermissionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
class PermissionsRepositoryTest {

    @Autowired
    private PermissionsRepository permissionsRepository;
    private PermissionEntity created,read,update,delete;

    @BeforeEach
    void setUp() {
        created= PermissionEntity.builder().name("CREATE").build();
        read= PermissionEntity.builder().name("READ").build();
        update= PermissionEntity.builder().name("UPDATE").build();
        delete= PermissionEntity.builder().name("DELETE").build();

    }

    @DisplayName("test para guardar Permiso")
    @Test
    void testSavePermission(){

        //Given

        //When
        PermissionEntity permissionTest= permissionsRepository.save(read);
        //Then
        assertThat(permissionTest).isNotNull();
        assertThat(permissionTest.getId()).isGreaterThan(0);
        assertThat(permissionTest.getName()).isEqualTo("READ");
    }

    @DisplayName("Test para buscar permiso")
    @Test
    void testFindPermission(){

        permissionsRepository.save(created);

        Optional<PermissionEntity> findPermission= permissionsRepository.findById(created.getId());
        assertThat(findPermission.isPresent()).isTrue();
        assertThat(findPermission.get().getName()).isEqualTo("CREATE");
    }

    @DisplayName("test para lista de permisos")
    @Test
    void testListPermissions(){
        permissionsRepository.saveAll(List.of(created, read, update, delete));
        List<PermissionEntity> permissions= permissionsRepository.findAll();
        assertThat(permissions).isNotNull();
        assertThat(permissions.size()).isEqualTo(4);//comprueba si hay 4 permisos
    }

    @DisplayName("test eliminar permiso")
    @Test
    void testDeletePermission(){
        PermissionEntity permissionTest= permissionsRepository.save(created);
        permissionsRepository.delete(permissionTest);
        Optional<PermissionEntity> deletePermission= permissionsRepository.findById(permissionTest.getId());

        assertThat(deletePermission.isPresent()).isFalse();
    }

}
