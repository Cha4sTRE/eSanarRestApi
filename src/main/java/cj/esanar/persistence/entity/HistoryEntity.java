package cj.esanar.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "medical_history")
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    /// {@link PatientEntity} relacionado con esta historia
    @OneToOne(cascade =CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "id_paciente", nullable = false)
    private PatientEntity patient;


    @OneToMany(mappedBy = "clinicalHistory", orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ConsultationEntity> evaluations = new HashSet<>();



    public void agregarConsultas(ConsultationEntity consulta) {
        this.evaluations.add(consulta);
        consulta.setClinicalHistory(this);
    }
}
