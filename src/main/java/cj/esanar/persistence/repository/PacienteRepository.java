package cj.esanar.persistence.repository;

import cj.esanar.persistence.entity.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface PacienteRepository extends JpaRepository<PatientEntity,Long> {
    @Query("SELECT p from PatientEntity p WHERE CONCAT(p.id,p.name,p.lastName,p.email,p.phoneNumber,p.identification) LIKE %?1%")
    Page<PatientEntity> findByfilter(Pageable page, String filtro);

}
