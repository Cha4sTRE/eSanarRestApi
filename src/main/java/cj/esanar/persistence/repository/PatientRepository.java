package cj.esanar.persistence.repository;

import cj.esanar.persistence.entity.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<PatientEntity,Long> {
}
