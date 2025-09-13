package cj.esanar.service.dtos.in;


public record PatientRequest(
        String firstName,
        String lastName,
        String documentType,
        Long identification,
        Long phoneNumber,
        String address,
        String neighborhood,
        String birthDate,
        int age,
        String gender,
        String bloodType,
        String email,
        String occupation,
        String eps,
        String maritalStatus
) {}
