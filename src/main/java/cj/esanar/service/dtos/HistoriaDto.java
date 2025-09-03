package cj.esanar.service.dtos;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class HistoriaDto {

    private LocalDate fechaCreacion;
    private PacienteDto paciente;
    private Set<ConsultaDto> consultas= new HashSet<>();

}
