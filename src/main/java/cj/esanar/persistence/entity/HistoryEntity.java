package cj.esanar.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "stories")
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
    private Set<ConsultationEntity> consultations = new HashSet<>();


}
