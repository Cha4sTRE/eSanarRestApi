package cj.esanar.dataProviders;

import cj.esanar.persistence.entity.ConsultationEntity;
import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.entity.auth.UserEntity;
import cj.esanar.service.dtos.out.ConsultationDto;
import cj.esanar.service.dtos.out.HistoryDto;
import cj.esanar.service.dtos.out.UserDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;

import static cj.esanar.dataProviders.PatientsDataProvider.*;

public class ConsultationDataProvider {

    private static UserEntity user;
    private static UserDto userDto;
    private static HistoryEntity history;
    private static HistoryDto historyDto;
    private static ConsultationEntity consultation;
    private static ConsultationDto consultationDto;

    static {
        user = UserEntity.builder()
                .name("Jefferson")
                .lastName("Chaustre")
                .username("jeffer")
                .identifier(1092524589L)
                .phoneNumber(3166846822L)
                .email("chaustrejefferson@gmail.com")
                .password("milluh123")
                .roles(Collections.emptySet())
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isAccountNonLocked(true)
                .build();
        consultation = ConsultationEntity.builder()
                .clinicalHistory(history)
                .attendedBy(user)
                .build();
        history = HistoryEntity.builder()
                .patient(patient1())
                .consultations(Set.of(consultation))
                .build();

        historyDto = new HistoryDto();
        historyDto.setCreationDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        historyDto.setConsultations(Collections.emptySet());
        userDto = new UserDto();
        consultationDto = new ConsultationDto();
        consultationDto.setAttendedBy(userDto);
        consultationDto.setPrimaryDiagnosis("diagnostico de prueba");
    }
    public static ConsultationEntity consultation(){return consultation;}
    public static HistoryEntity history(){return history;}
    public static HistoryDto historyDto(){return historyDto;}
    public static ConsultationDto consultationDto(){return consultationDto;}
}
