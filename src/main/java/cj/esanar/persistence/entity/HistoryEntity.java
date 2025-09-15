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
@Table(name = "history")
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate creationDate;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_patient", nullable = false)
    private PatientEntity patient;


    @OneToMany(mappedBy = "clinicalHistory", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ConsultationEntity> consultations = new HashSet<>();

    public void addConsultations(ConsultationEntity consultation) {
        this.consultations.add(consultation);
        consultation.setClinicalHistory(this);
    }

}
