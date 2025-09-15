package cj.esanar.persistence.repository;

import cj.esanar.persistence.entity.ConsultationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ConsultationRepository extends JpaRepository<ConsultationEntity,Long> {


}
