package cj.esanar.service.dtos.in;

import jakarta.validation.constraints.NotBlank;

public record PatientHistoryRequest(
        @NotBlank(message = "El id del paciente no puede estar vacio" )
        Long id
) {}
