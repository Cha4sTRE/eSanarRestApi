package cj.esanar.controller;


import cj.esanar.persistence.entity.HistoriaEntity;
import cj.esanar.persistence.entity.PacienteEntity;
import cj.esanar.service.HistoryService;
import cj.esanar.service.PatientService;
import cj.esanar.util.reports.ExportarPacientesExel;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/patient")
@PreAuthorize("hasAnyRole('ENF','MEDIC','ADMIN','VISITOR')")
public class patientsController {

    private final PatientService patientServiceImpl;
    private final HistoryService historyServiceImpl;
    private UserDetailsService userDetailsService;

    @GetMapping("/")
    public ResponseEntity<List<PacienteEntity>> home(@RequestParam(name = "page",defaultValue ="0") int page, @RequestParam(name = "filter",defaultValue = "all")String filter) {

        List<PacienteEntity> patients=  patientServiceImpl.listPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/nuevo")
    public ResponseEntity<PacienteEntity> nuevo(@RequestBody PacienteEntity paciente) {
        patientServiceImpl.savePatients(paciente);
        return ResponseEntity.ok(paciente);
    }

    @GetMapping("find/{name}")
    public String paciente(PacienteEntity paciente, @RequestParam Long historia, Model model, @PathVariable String nombre) {

        HistoriaEntity historiaEspecifica= historyServiceImpl.findHistoryById(historia);
        DateTimeFormatter formato=DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return "enf/paciente-form";
    }

    @GetMapping("/delete")
    public String eliminar(PacienteEntity paciente) {
        patientServiceImpl.deletePatients(paciente);
        return "redirect:/enf/";
    }


    @GetMapping("paciente/ExportarExcel")
    public void exportarExcel(HttpServletResponse response) throws IOException {

        List<PacienteEntity> pacientes= patientServiceImpl.listPatients();

        response.setContentType("application/octec-stream");
        DateTimeFormatter formato= DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        String fecha= formato.format(LocalDateTime.now());

        String cabecera= "Content-Disposition";
        String valor="attachment; filename=Consulta_"+fecha+".xlsx";
        response.setHeader(cabecera,valor);
        ExportarPacientesExel exportar= new ExportarPacientesExel(pacientes);
        exportar.exportar(response);

    }

}
