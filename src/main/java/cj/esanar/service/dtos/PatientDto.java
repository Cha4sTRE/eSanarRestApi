package cj.esanar.service.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String documentType;
    private Long identification;
    private Long phoneNumber;
    private String address;
    private String neighborhood;
    private LocalDate birthDate;
    private int age;
    private String gender;
    private String bloodType;
    private String email;
    private String occupation;
    private String organization;
    private String maritalStatus;
    private HistoryDto historyEntity;


}
