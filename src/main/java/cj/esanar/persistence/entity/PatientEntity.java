package cj.esanar.persistence.entity;


import cj.esanar.persistence.entity.enums.BloodType;
import cj.esanar.persistence.entity.enums.DocumentType;
import cj.esanar.persistence.entity.enums.Gender;
import cj.esanar.persistence.entity.enums.MaritalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "patients")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long identification;
    private String address; //Direccion
    private String neighborhood; //Barrio
    private int age;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;
    private String email;
    private String occupation;
    private String eps;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "document_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    @NotNull
    private DocumentType documentType;

    @Column(name = "phone_number", nullable = false, length = 20)
    private Long phoneNumber;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate birthDate;

    @Column(name = "blood_type", nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    @NotNull
    private BloodType bloodType;

    @Column(name = "marital_status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private MaritalStatus maritalStatus;


    @OneToOne(mappedBy = "patient", orphanRemoval = true,cascade = CascadeType.PERSIST)
    private HistoryEntity historyEntity;

}
