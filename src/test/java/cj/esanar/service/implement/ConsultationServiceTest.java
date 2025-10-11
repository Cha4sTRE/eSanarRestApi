package cj.esanar.service.implement;

import cj.esanar.dataProviders.ConsultationDataProvider;
import cj.esanar.dataProviders.UserDetailsDataProvider;
import cj.esanar.persistence.entity.ConsultationEntity;
import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.persistence.entity.auth.UserEntity;
import cj.esanar.persistence.repository.ConsultationRepository;
import cj.esanar.persistence.repository.PatientRepository;
import cj.esanar.persistence.repository.UserRepository;
import cj.esanar.service.dtos.in.ConsultationHistoryRequest;
import cj.esanar.service.dtos.in.ConsultationRequest;
import cj.esanar.service.dtos.in.PatientHistoryRequest;
import cj.esanar.service.dtos.in.PatientRequest;
import cj.esanar.service.dtos.out.ConsultationDto;
import cj.esanar.service.dtos.out.PatientDto;
import cj.esanar.util.ConsultationMapper;
import cj.esanar.persistence.entity.enums.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static cj.esanar.dataProviders.ConsultationDataProvider.*;
import static cj.esanar.dataProviders.PatientsDataProvider.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultationServiceTest {

    @Mock
    private ConsultationRepository consultationRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ConsultationMapper consultationMapper;

    @InjectMocks
    private ConsultationServiceImpl consultationService;

    // ----------- TEST: Guardar consulta -----------
    @DisplayName("Test para guardar consulta")
    @Test
    void testSaveConsultation() {
        // Given
        PatientEntity patientEntity = patient1();
        ConsultationEntity consultationEntity = consultation();
        ConsultationDto consultationDto = consultationDto();
        UserEntity userEntity = UserDetailsDataProvider.userAdmin();

        ConsultationRequest consultationRequest = mock(ConsultationRequest.class);
        ConsultationHistoryRequest consultationHistoryRequest = mock(ConsultationHistoryRequest.class);
        PatientHistoryRequest patientHistoryRequest = mock(PatientHistoryRequest.class);

        // Inicializa el historial del paciente
        HistoryEntity history = new HistoryEntity();
        history.setConsultations(new HashSet<>());
        patientEntity.setHistory(history);

        // Configuración del contexto de seguridad
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(userEntity.getUsername());
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // When
        when(consultationRequest.history()).thenReturn(consultationHistoryRequest);
        when(consultationHistoryRequest.patient()).thenReturn(patientHistoryRequest);
        when(patientHistoryRequest.id()).thenReturn(1L);

        when(consultationMapper.toEntity(consultationRequest)).thenReturn(consultationEntity);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patientEntity));
        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));
        when(consultationMapper.toDto(consultationEntity)).thenReturn(consultationDto);

        ConsultationDto consultationDtoTest = consultationService.saveConsultation(consultationRequest);

        // Then
        assertThat(consultationDtoTest).isNotNull();
        verify(consultationRepository).save(consultationEntity);
        verify(patientRepository).save(patientEntity);
    }

    // ----------- TEST: Listar consultas -----------
    @DisplayName("Test para listar todas las consultas")
    @Test
    void testListConsultations() {
        // Given
        List<ConsultationEntity> entityList = List.of(mock(ConsultationEntity.class), mock(ConsultationEntity.class));
        List<ConsultationDto> dtoList = List.of(mock(ConsultationDto.class), mock(ConsultationDto.class));

        when(consultationRepository.findAll()).thenReturn(entityList);
        when(consultationMapper.toDto(entityList)).thenReturn(dtoList);

        // When
        List<ConsultationDto> result = consultationService.listConsultations();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(consultationRepository).findAll();
        verify(consultationMapper).toDto(entityList);
    }

    // ----------- TEST: Buscar consulta por ID -----------
    @DisplayName("Test para buscar una consulta por id")
    @Test
    void testFindConsultationById() {
        // Given
        ConsultationEntity entity = mock(ConsultationEntity.class);
        ConsultationDto dto = mock(ConsultationDto.class);

        when(consultationRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(consultationMapper.toDto(entity)).thenReturn(dto);

        // When
        ConsultationDto result = consultationService.findConsultationById(1L);

        // Then
        assertThat(result).isNotNull();
        verify(consultationRepository).findById(1L);
        verify(consultationMapper).toDto(entity);
    }

    // ----------- TEST: Actualizar consulta -----------
    @DisplayName("Test para actualizar una consulta")
    @Test
    void testUpdateConsultation() {
        Long id= 1L;
        ConsultationRequest consultationRequest= mock(ConsultationRequest.class);
        ConsultationHistoryRequest consultationHistoryRequest= mock(ConsultationHistoryRequest.class);
        PatientHistoryRequest patientHistoryRequest = mock(PatientHistoryRequest.class);
        PatientEntity patientEntity = patient1();
        ConsultationEntity consultationEntity = consultation();
        consultationEntity.setId(id);
        ConsultationDto consultationDto = consultationDto();
        consultationDto.setId(id);

        HistoryEntity history = new HistoryEntity();
        history.setConsultations(new HashSet<>());
        patientEntity.setHistory(history);

        when(consultationMapper.toEntity(consultationRequest)).thenReturn(consultationEntity);

        when(consultationRequest.history()).thenReturn(consultationHistoryRequest);
        when(consultationHistoryRequest.patient()).thenReturn(patientHistoryRequest);
        when(patientHistoryRequest.id()).thenReturn(id);
        when(patientRepository.findById(id)).thenReturn(Optional.of(patientEntity));
        when(consultationMapper.toDto(consultationEntity)).thenReturn(consultationDto);

        ConsultationDto consultationDtoTest= consultationService.updateConsultation(consultationRequest,id);

        assertThat(consultationDtoTest).isNotNull();
        assertThat(consultationDtoTest).isEqualTo(consultationDto);
        assertThat(consultationDtoTest.getId()).isEqualTo(id);
    }
    // ----------- TEST: Eliminar consulta por ID -----------
    @DisplayName("Test para eliminar una consulta por id")
    @Test
    void testDeleteConsultationById() {
        // When
        consultationService.deleteConsultationById(3L);
        // Then
        verify(consultationRepository).deleteById(3L);
    }
}
