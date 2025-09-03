package cj.esanar.service.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PacienteDto {

    private Long id;
    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private Long identificacion;
    private Long telefono;
    private String direccion;
    private String barrio;
    private LocalDate fechaNacimiento;
    private int edad;
    private String sexo;
    private String tipoSangre;
    private String correo;
    private String ocupacion;
    private String entidad;
    private String estadoCivil;
    private HistoriaDto historiaEntity;

}
