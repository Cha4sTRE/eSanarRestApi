package cj.esanar.service.dtos.out;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
public class PatientDto {

    private String firstName;
    private String lastName;
    private String documentType;
    private Long identification;
    private Long phoneNumber;
    private String address;
    private String neighborhood;
    private String birthDate;
    private int age;
    private String gender;
    private String bloodType;
    private String email;
    private String occupation;
    private String eps;
    private String maritalStatus;
    private HistoryDto history;

}
