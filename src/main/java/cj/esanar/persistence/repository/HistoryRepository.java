package cj.esanar.persistence.repository;

import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
}
