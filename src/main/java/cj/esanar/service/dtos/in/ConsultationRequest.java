package cj.esanar.service.dtos.in;

import jakarta.validation.constraints.*;

public record ConsultationRequest(
        @NotNull(message = "La asosiacion de consulta con una historia es requerido")
        ConsultationHistoryRequest history,

        @NotBlank(message = "El diagnostico principal no puede estar vacio")
        String primaryDiagnosis,

        @NotBlank(message = "El motivo principal no puede estar vacio")
        String motive,

        @Positive
        Double length,

        @Positive
        Double width,

        @NotBlank(message = "la profundidad no puede estar vacio")
        String depth,

        @NotBlank(message = "La forma no puede estar vacio")
        String form,
        String smell,
        String woundEdges,
        @NotBlank(message = "La infeccion no puede estar vacio")
        String infection,
        String exudateType,
        String exudateLevel

) {}
