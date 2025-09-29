package cj.esanar.persistence;

import cj.esanar.persistence.entity.auth.PermissionEntity;
import cj.esanar.persistence.repository.PermissionsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class PermissionsRepositoryTest {

    @Autowired
    private PermissionsRepository permissionsRepository;

    @Test
    void testSavePermission(){
        //Given
        PermissionEntity permissionTest= PermissionEntity.builder().name("READ").build();
        //When
        permissionsRepository.save(permissionTest);
        //Then
        assertThat(permissionTest).isNotNull();
    }

}
