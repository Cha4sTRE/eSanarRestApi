package cj.esanar.persistence.repository;

import cj.esanar.persistence.entity.EvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ConsultationRepository extends JpaRepository<EvaluationEntity,Long> {
   /* @Query("""
    SELECT c FROM EvaluationEntity c
    WHERE c.historiaClinica.id = :id
    """)
    Page<EvaluationEntity> findAllById(Pageable pageable, Long id);

    @Query("""
    SELECT c FROM EvaluationEntity c
    WHERE c.historiaClinica.id = :id
    AND (
        LOWER(c.motivoConsulta) LIKE LOWER(CONCAT('%', :filtro, '%'))
        OR LOWER(c.diagnosticoPrincipal) LIKE LOWER(CONCAT('%', :filtro, '%'))
        OR LOWER(c.enfermera.username) LIKE LOWER(CONCAT('%', :filtro, '%'))
    )
""")
    Page<EvaluationEntity> findAllById(Pageable pageable, Long id, @Param("filtro") String filtro);
*/
}
