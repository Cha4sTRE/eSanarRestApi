package cj.esanar.persistence.repository;

import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.entity.PatientEntity;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;


    private PatientEntity patientEntity;
    private HistoryEntity historyEntity;

    @BeforeEach
    void setUp() {
        patientEntity= PatientEntity.builder()
                .documentType(DocumentType.CC)
                .identification(1092524589L)
                .firstName("jefferson")
                .lastName("Chaustre")
                .age(20)
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

    }

    @DisplayName("Test para guardar paciente")
    @Test
    void testSavePatient(){
        historyEntity= HistoryEntity.builder().consultations(Collections.emptySet()).patient(patientEntity).creationDate(LocalDate.now()).build();
        patientEntity.setHistory(historyEntity);
        PatientEntity patientTest= patientRepository.save(patientEntity);

        assertThat(patientTest).isNotNull();
        assertThat(patientTest.getId()).isGreaterThan(0);
        assertThat(patientTest.getDocumentType()).isInstanceOf(DocumentType.class);
        assertThat(patientTest.getBloodType()).isInstanceOf(BloodType.class);
        assertThat(patientTest.getGender()).isInstanceOf(Gender.class);
        assertThat(patientTest.getMaritalStatus()).isInstanceOf(MaritalStatus.class);
        assertThat(patientTest.getBirthDate()).isInstanceOf(LocalDate.class);
        assertThat(patientTest.getHistory()).isInstanceOf(HistoryEntity.class);


    }

    @DisplayName("Test para buscar paciente")
    @Test
    void testFindById(){
        historyEntity= HistoryEntity.builder().consultations(Collections.emptySet()).patient(patientEntity).creationDate(LocalDate.now()).build();
        patientEntity.setHistory(historyEntity);
        PatientEntity patientTest= patientRepository.save(patientEntity);

        Optional<PatientEntity> patientEntityOptional = patientRepository.findById(patientEntity.getId());

        assertThat(patientEntityOptional.isPresent()).isTrue();
        assertThat(patientEntityOptional.get().getId()).isEqualTo(patientEntity.getId());
        assertThat(patientEntityOptional.get().getIdentification()).isEqualTo(patientEntity.getIdentification());
        assertThat(patientEntityOptional.get().getFirstName()).isEqualTo(patientEntity.getFirstName());

    }

    @DisplayName("Test para lista de pacientes")
    @Test
    void testFindAll(){
        historyEntity= HistoryEntity.builder().consultations(Collections.emptySet()).patient(patientEntity).creationDate(LocalDate.now()).build();
        patientEntity.setHistory(historyEntity);
        List<PatientEntity> listPatientsTest = patientRepository.saveAll(List.of(patientEntity));

        assertThat(listPatientsTest).isInstanceOf(List.class);
        assertThat(listPatientsTest.isEmpty()).isFalse();
    }

}