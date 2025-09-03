package cj.esanar.persistence.repository;

import cj.esanar.persistence.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriaRepository extends JpaRepository<HistoryEntity, Long> {

}
