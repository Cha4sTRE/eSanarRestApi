package cj.esanar.service.implement;

import cj.esanar.persistence.entity.ConsultationEntity;
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

import java.util.List;
import java.util.Optional;

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
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private ConsultationServiceImpl consultationService;

    // ----------- TEST: Guardar consulta -----------
    /*
    @DisplayName("Test para guardar una consulta")
    @Test
    void testSaveConsultation() {
        // Given
        PatientHistoryRequest patientHistoryRequest = mock(PatientHistoryRequest.class);
        when(patientHistoryRequest.id()).thenReturn(1L);

        ConsultationRequest request = getConsultationRequest(patientHistoryRequest);

        ConsultationEntity entity = mock(ConsultationEntity.class);
        ConsultationDto dto = mock(ConsultationDto.class);
        PatientEntity patient = mock(PatientEntity.class);
        UserEntity user = mock(UserEntity.class);

        // Mock del contexto de seguridad
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("userTest");
        SecurityContextHolder.setContext(securityContext);

        when(consultationMapper.toEntity(request)).thenReturn(entity);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(userRepository.findByUsername("userTest")).thenReturn(Optional.of(user));
        when(consultationRepository.save(any(ConsultationEntity.class))).thenReturn(entity);
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(patient);
        when(consultationMapper.toDto(any(ConsultationEntity.class))).thenReturn(dto);

        // When
        ConsultationDto result = consultationService.saveConsultation(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(ConsultationDto.class);

        verify(consultationMapper).toEntity(request);
        verify(patientRepository).findById(1L);
        verify(userRepository).findByUsername("userTest");
        verify(consultationRepository).save(entity);
        verify(patientRepository).save(patient);
        verify(consultationMapper).toDto(entity);
    }

    private static ConsultationRequest getConsultationRequest(PatientHistoryRequest patientHistoryRequest) {
        ConsultationHistoryRequest historyRequest = new ConsultationHistoryRequest(patientHistoryRequest);
        ConsultationRequest request = new ConsultationRequest(
                historyRequest,
                "Diagnóstico activo",
                "Control general",
                10.0,
                5.0,
                "3mm",
                "Circular",
                "Sin olor",
                "Definidos",
                "Sin infección",
                "Seroso",
                "Moderado"
        );
        return request;
    }
    */
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
    /*
    @DisplayName("Test para actualizar una consulta")
    @Test
    void testUpdateConsultation() {
        // Given
        PatientHistoryRequest patientHistoryRequest = mock(PatientHistoryRequest.class);
        when(patientHistoryRequest.id()).thenReturn(5L);

        ConsultationHistoryRequest historyRequest = new ConsultationHistoryRequest(patientHistoryRequest);
        ConsultationRequest request = new ConsultationRequest(
                historyRequest,
                "Revisión de evolución",
                "Chequeo general",
                12.0,
                8.0,
                "4mm",
                "Ovalada",
                "Olor leve",
                "Difusos",
                "Con infección",
                "Hemático",
                "Abundante"
        );

        ConsultationEntity entity = mock(ConsultationEntity.class);
        ConsultationDto dto = mock(ConsultationDto.class);
        PatientEntity patient = mock(PatientEntity.class);

        when(consultationMapper.toEntity(request)).thenReturn(entity);
        when(patientRepository.findById(5L)).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);
        when(consultationRepository.save(entity)).thenReturn(entity);
        when(consultationMapper.toDto(entity)).thenReturn(dto);

        // When
        ConsultationDto result = consultationService.updateConsultation(request, 10L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(ConsultationDto.class);
        verify(consultationMapper).toEntity(request);
        verify(patientRepository).findById(5L);
        verify(consultationRepository).save(entity);
        verify(consultationMapper).toDto(entity);
    }
    */
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
