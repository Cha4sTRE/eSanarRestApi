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
@Table(name = "patient")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address; //Direccion
    private String neighborhood; //Barrio
    private int age;
    private String email;
    private String occupation;
    private String eps;

    @Column(unique = true)
    private Long identification;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "document_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(name = "phone_number", nullable = false, length = 20)
    private Long phoneNumber;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate birthDate;

    @Column(name = "blood_type", nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private BloodType bloodType;

    @Column(name = "marital_status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_history")
    private HistoryEntity history;

}
