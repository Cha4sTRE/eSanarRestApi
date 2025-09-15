package cj.esanar.service.dtos.in;

public record ConsultationRequest(

        ConsultationHistoryRequest consultationHistoryRequest,
        String primaryDiagnosis,
        String motive,
        Integer length,
        Integer width,
        String depth,
        String form,
        String smell,
        String woundEdges,
        String infection,
        String exudateType,
        String exudateLevel

) {}
