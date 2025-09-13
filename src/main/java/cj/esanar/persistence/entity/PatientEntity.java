package cj.esanar.persistence.entity;


import jakarta.persistence.*;
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
    private String gender;
    private String email;
    private String occupation;
    private String eps;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "document_type", nullable = false, length = 50)
    private String documentType;

    @Column(name = "phone_number", nullable = false, length = 20)
    private Long phoneNumber;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate birthDate;

    @Column(name = "blood_type", nullable = false, length = 5)
    private String bloodType;

    @Column(name = "marital_status", nullable = false, length = 20)
    private String maritalStatus;


    @OneToOne(mappedBy = "patient", orphanRemoval = true,cascade = CascadeType.PERSIST)
    private HistoryEntity historyEntity;

}
