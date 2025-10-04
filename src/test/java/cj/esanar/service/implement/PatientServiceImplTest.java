package cj.esanar.service.implement;

import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.persistence.entity.enums.BloodType;
import cj.esanar.persistence.entity.enums.DocumentType;
import cj.esanar.persistence.entity.enums.Gender;
import cj.esanar.persistence.entity.enums.MaritalStatus;
import cj.esanar.persistence.repository.PatientRepository;
import cj.esanar.service.dtos.in.PatientRequest;
import cj.esanar.service.dtos.out.PatientDto;
import cj.esanar.util.PatientMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;


import java.time.LocalDate;

import static cj.esanar.dataProviders.DataProviderPatient.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private PatientMapper patientMapper;
    @InjectMocks
    PatientServiceImpl patientServiceImpl;

    @Test
    void testSavePatients(){


        when(patientMapper.toPatient(patientRequest())).thenReturn(patient1());
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(patient1());
        when(patientMapper.toPatientDto(any(PatientEntity.class))).thenReturn(dto1());

        PatientDto result= patientServiceImpl.savePatients(patientRequest());
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(PatientDto.class);
        assertThat(result).isEqualTo(dto1());
        assertThat(result.getIdentification()).isEqualTo(dto1().getIdentification());

        verify(patientMapper).toPatient(patientRequest());
        verify(patientRepository).save(any(PatientEntity.class));
        verify(patientMapper).toPatientDto(any(PatientEntity.class));
    }

    @Test
    void testListPatients() {
        //Given
        Pageable pageable = PageRequest.of(0, 3);
        Page<PatientEntity> pagePatient= new PageImpl<>(patientList(),pageable,3);
        Page<PatientDto> pagePatientDto= new PageImpl<>(patientDtoList(),pageable,3);

        //When
        when(patientRepository.findAll(pageable)).thenReturn(pagePatient);
        when(patientMapper.toPatientDto(patient1())).thenReturn(dto1());
        when(patientMapper.toPatientDto(patient2())).thenReturn(dto2());

        Page<PatientDto> pageTest= patientServiceImpl.listPatients(pageable);
        //Then
        assertThat(pageTest).isNotNull();
        assertThat(pageTest.getTotalElements()).isEqualTo(3);
        assertThat(pageTest).isInstanceOf(PageImpl.class);


    }

    @Test
    void findPatientsById() {


    }

}