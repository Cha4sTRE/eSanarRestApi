package cj.esanar.persistence.repository;

import cj.esanar.persistence.entity.auth.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionsRepository extends JpaRepository<PermissionEntity, Long> {
}
