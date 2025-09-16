package cj.esanar.service.dtos.in;

import jakarta.validation.constraints.NotNull;

public record ConsultationHistoryRequest (
    @NotNull(message = "La asociasion de la historia con un paciente es requerida")
    PatientHistoryRequest patient
){}
