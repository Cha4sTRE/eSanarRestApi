package cj.esanar.persistence.entity;

import cj.esanar.persistence.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "evaluations")
public class ConsultationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String motive;
    private Integer length;
    private Integer width;
    private String depth;
    private String form;
    private String smell;
    private String infection;

    @Column(name = "wound_edges")
    private String woundEdges;

    @Column(name = "primary_diagnosis")
    private String primaryDiagnosis;

    @Column(name = "exudate_type")
    private String exudateType;

    @Column(name = "exudate_level")
    private String exudateLevel;

    @Column(name = "service_date")
    private LocalDateTime serviceDate;

    @Column(name = "final_time")
    private LocalTime finalTime;

    @ManyToOne
    @JoinColumn(name = "id_nurse")
    private UserEntity enfermera;

    ///
    /// Atributo que relaciona {@link HistoryEntity} con muchas consultas, esta relacion es de **Muchos a Uno**
    /// ósea que **muchas** consultas pertenecen a **una** historia
    ///
    @ManyToOne
    @JoinColumn(name = "id_historia", nullable = false)
    private HistoryEntity clinicalHistory;

}
