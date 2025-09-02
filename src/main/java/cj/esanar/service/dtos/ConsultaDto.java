package cj.esanar.service.dtos;


import java.time.LocalDateTime;
import java.time.LocalTime;

public class ConsultaDto {

    private HistoriaDto historiaClinica;
    private UserDto enfermera;
    private String diagnosticoPrincipal;
    private String motivoConsulta;
    private Integer largo;
    private Integer ancho;
    private String profundidad;
    private String forma;
    private String olor;
    private String bordesHerida;
    private String infeccion;
    private String exudadoTipo;
    private String exudadoNivel;
    private LocalDateTime fechaHoraAtencion;
    private LocalTime horaFinal;


}
