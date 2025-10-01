package cj.esanar.service.implement;

import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.persistence.entity.enums.BloodType;
import cj.esanar.persistence.entity.enums.DocumentType;
import cj.esanar.persistence.entity.enums.Gender;
import cj.esanar.persistence.entity.enums.MaritalStatus;
import cj.esanar.persistence.repository.PatientRepository;
import cj.esanar.service.dtos.out.PatientDto;
import cj.esanar.util.PatientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private PatientMapper patientMapper;
    @InjectMocks
    PatientServiceImpl patientServiceImpl;

    private PatientEntity patient1,patient2,patient3;
    private PatientDto dto1,dto2,dto3;

    @BeforeEach
    void setUp() {
        patient1= PatientEntity.builder()
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
        patient2= PatientEntity.builder()
                .documentType(DocumentType.CC)
                .identification(1093584758L)
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
        patient3= PatientEntity.builder()
                .documentType(DocumentType.CC)
                .identification(1094568956L)
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

        dto1 = new PatientDto();
        dto1.setDocumentType(DocumentType.CC.name());
        dto1.setIdentification(1092524589L);
        dto1.setFirstName("jefferson");
        dto1.setLastName("Chaustre");
        dto1.setAge(20);
        dto1.setBirthDate(LocalDate.of(2004, 2, 20).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto1.setBloodType(BloodType.O_POS.name());
        dto1.setGender(Gender.Male.name());
        dto1.setMaritalStatus(MaritalStatus.Soltero.name());
        dto1.setEmail("chaustrejefferson@gmail.com");
        dto1.setPhoneNumber(3166846822L);
        dto1.setEps("Sanidadd");
        dto1.setAddress("calle 15");
        dto1.setNeighborhood("Niza");
        dto1.setOccupation("Estudiante");

        dto2 = new PatientDto();
        dto2.setDocumentType(DocumentType.CC.name());
        dto2.setIdentification(1093584758L);
        dto2.setFirstName("jefferson");
        dto2.setLastName("Chaustre");
        dto2.setAge(20);
        dto2.setBirthDate(LocalDate.of(2004, 2, 20).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto2.setBloodType(BloodType.O_POS.name());
        dto2.setGender(Gender.Male.name());
        dto2.setMaritalStatus(MaritalStatus.Soltero.name());
        dto2.setEmail("chaustrejefferson@gmail.com");
        dto2.setPhoneNumber(3166846822L);
        dto2.setEps("Sanidadd");
        dto2.setAddress("calle 15");
        dto2.setNeighborhood("Niza");
        dto2.setOccupation("Estudiante");

        dto3 = new PatientDto();
        dto3.setDocumentType(DocumentType.CC.name());
        dto3.setIdentification(1094568956L);
        dto3.setFirstName("jefferson");
        dto3.setLastName("Chaustre");
        dto3.setAge(20);
        dto3.setBirthDate(LocalDate.of(2004, 2, 20).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto3.setBloodType(BloodType.O_POS.name());
        dto3.setGender(Gender.Male.name());
        dto3.setMaritalStatus(MaritalStatus.Soltero.name());
        dto3.setEmail("chaustrejefferson@gmail.com");
        dto3.setPhoneNumber(3166846822L);
        dto3.setEps("Sanidadd");
        dto3.setAddress("calle 15");
        dto3.setNeighborhood("Niza");
        dto3.setOccupation("Estudiante");
    }

    @Test
    void testListPatients() {
        //Given
        Pageable pageable = PageRequest.of(0, 3);
        Page<PatientEntity> pagePatient= new PageImpl<>(List.of(patient1,patient2,patient3),pageable,3);
        Page<PatientDto> pagePatientDto= new PageImpl<>(List.of(dto1,dto2,dto3),pageable,3);

        //When
        when(patientRepository.findAll(pageable)).thenReturn(pagePatient);
        when(patientMapper.toPatientDto(patient1)).thenReturn(dto1);
        when(patientMapper.toPatientDto(patient2)).thenReturn(dto2);
        when(patientMapper.toPatientDto(patient3)).thenReturn(dto3);

        Page<PatientDto> pageTest= patientServiceImpl.listPatients(pageable);
        //Then
        assertThat(pageTest).isNotNull();
        assertThat(pageTest).isEqualTo(pagePatientDto);
        assertThat(pageTest.getTotalElements()).isEqualTo(3);

    }

    @Test
    void findPatientsById() {
    }

    @Test
    void savePatients() {
    }
}