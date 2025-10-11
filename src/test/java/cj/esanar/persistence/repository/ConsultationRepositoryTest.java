package cj.esanar.persistence.repository;

import cj.esanar.persistence.entity.ConsultationEntity;
import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.persistence.entity.auth.UserEntity;
import cj.esanar.persistence.entity.enums.BloodType;
import cj.esanar.persistence.entity.enums.DocumentType;
import cj.esanar.persistence.entity.enums.Gender;
import cj.esanar.persistence.entity.enums.MaritalStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ConsultationRepositoryTest {

    @Autowired
    private ConsultationRepository consultationRepository;
    @Autowired
    private HistoryRepository historyRepository;

    private ConsultationEntity consultationEntity;
    private HistoryEntity history;
    @BeforeEach
    void setUp() {
        UserEntity user= UserEntity.builder().build();
        PatientEntity patient= PatientEntity.builder()
                .documentType(DocumentType.CC)
                .identification(1092524589L)
                .firstName("jefferson")
                .lastName("Chaustre")
                .birthDate(LocalDate.of(2004,02,20))
                .bloodType(BloodType.O_POS)
                .gender(Gender.Male)
                .maritalStatus(MaritalStatus.Soltero)
                .email("chaustrejefferson@gmail.com")
                .phoneNumber(3166846822L)
                .eps("Sanidadd")
                .address("calle 15")
                .neighborhood("Niza")
                .occupation("Estudiante")
                .build();
        history= HistoryEntity.builder().patient(patient).build();
        historyRepository.save(history);
        consultationEntity= ConsultationEntity.builder()
                .primaryDiagnosis("Dolor en herida vieja")
                .motive("presenta dolores repentinos")
                .length(1.5)
                .width(2.1)
                .depth("superficial")
                .form("lineal")
                .infection("infection")
                .woundEdges("wound edges")
                .exudateLevel("na")
                .exudateType("na")
                .attendedBy(user)
                .clinicalHistory(history)
                .build();

    }

    @DisplayName("Test para guadar consulta")
    @Test
    void testSaveConsultation() {

        ConsultationEntity consultationTest= consultationRepository.save(consultationEntity);
        assertThat(consultationTest).isNotNull();
        assertThat(consultationTest.getId()).isGreaterThan(0);
        assertThat(consultationTest.getAttendedBy()).isInstanceOf(UserEntity.class);
        assertThat(consultationTest.getClinicalHistory()).isInstanceOf(HistoryEntity.class);

    }

    @DisplayName("Test para buscar consulta")
    @Test
    void testFindConsultationById() {
        consultationRepository.save(consultationEntity);
        Optional<ConsultationEntity> consultationFindTest= consultationRepository.findById(consultationEntity.getId());
        assertThat(consultationFindTest.isPresent()).isTrue();
        assertThat(consultationFindTest.get().getPrimaryDiagnosis()).isEqualTo(consultationEntity.getPrimaryDiagnosis());
    }

    @DisplayName("Test para lista de consultas")
    @Test
    void testFindAllConsultations() {
        ConsultationEntity consultation1= ConsultationEntity.builder()
                .clinicalHistory(history)
                .build();
        ConsultationEntity consultation2= ConsultationEntity.builder()
                .clinicalHistory(history)
                .build();
        ConsultationEntity consultation3= ConsultationEntity.builder()
                .clinicalHistory(history)
                .build();

        consultationRepository.saveAll(List.of(consultation1,consultation2,consultation3));
        List<ConsultationEntity> consultationList= consultationRepository.findAll();

        assertThat(consultationList).isNotNull();
        assertThat(consultationList).isInstanceOf(List.class);
        assertThat(consultationList.size()).isEqualTo(3);
    }

    @DisplayName("Test para eliminar consulta")
    @Test
    void testDeleteConsultation() {
        ConsultationEntity contationDelete = consultationRepository.save(consultationEntity);
        consultationRepository.delete(contationDelete);
        Optional<ConsultationEntity> consultationFind= consultationRepository.findById(consultationEntity.getId());

        assertThat(consultationFind.isPresent()).isFalse();
    }
}