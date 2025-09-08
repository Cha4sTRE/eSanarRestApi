package cj.esanar.persistence.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "patients")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long identification;
    private String name;
    private String address;
    private String neighborhood;
    private int age;
    private String sex;
    private String email;
    private String occupation;
    private String eps;

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
    private HistoryEntity historiaEntity;

}
