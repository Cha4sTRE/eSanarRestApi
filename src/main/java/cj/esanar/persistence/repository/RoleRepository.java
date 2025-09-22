package cj.esanar.persistence.repository;

import cj.esanar.persistence.entity.auth.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    List<RoleEntity> findRoleEntitiesByNameIn(List<String> roleList);

}
