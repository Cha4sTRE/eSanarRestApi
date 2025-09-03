package cj.esanar.controller;


import cj.esanar.persistence.entity.HistoriaEntity;
import cj.esanar.persistence.entity.PacienteEntity;
import cj.esanar.service.HistoryService;
import cj.esanar.service.PatientService;
import cj.esanar.util.pagination.PageRender;
import cj.esanar.util.reports.ExportarPacientesExel;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/enf")
@PreAuthorize("hasAnyRole('ENF','MEDIC','ADMIN','VISITOR')")
public class EnfController {

    private final PatientService patientServiceImpl;
    private final HistoryService historyServiceImpl;
    private UserDetailsService userDetailsService;

    @GetMapping("/")
    public String home(@RequestParam(name = "page",defaultValue ="0") int page,@RequestParam(name = "filter",defaultValue = "all")String filter) {


        List<PacienteEntity> pacientes=  patientServiceImpl.listPatients();

        return "pacientes";
    }

    @GetMapping("paciente/nuevo")
    public String nuevo(PacienteEntity paciente,Model model) {
        model.addAttribute("paciente", paciente);
        return "enf/paciente-form";
    }

    @GetMapping("paciente/{nombre}")
    public String paciente(PacienteEntity paciente, @RequestParam Long historia, Model model, @PathVariable String nombre) {

        HistoriaEntity historiaEspecifica= historyServiceImpl.findHistoryById(historia);
        DateTimeFormatter formato=DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return "enf/paciente-form";
    }

    @GetMapping("paciente/eliminar")
    public String eliminar(PacienteEntity paciente) {
        patientServiceImpl.deletePatients(paciente);
        return "redirect:/enf/";
    }

    @PostMapping("paciente/guardar")
    public String guardar(@Valid PacienteEntity paciente, @RequestParam(value = "idHistoria",required = false) Long idHistoria,@RequestParam String fechaNacimiento, Errors errors) {
        if (errors.hasErrors()) {
            return "enf/paciente-form";
        }
        LocalDate dateNacimiento= LocalDate.parse(fechaNacimiento);
        LocalDate hoy=LocalDate.now();
        paciente.setFechaNacimiento(dateNacimiento);
        if (paciente.getId() == null) {
            // CASO NUEVO
            HistoriaEntity historiaNueva= new HistoriaEntity(null, hoy, paciente, Collections.emptySet());
            historiaNueva.setPaciente(paciente);
            paciente.setHistoriaEntity(historiaNueva);
        } else {
            // CASO EDICIÓN
            HistoriaEntity historiaExistente = historyServiceImpl.findHistoryById(idHistoria);
            paciente.setHistoriaEntity(historiaExistente);
        }
        patientServiceImpl.savePatients(paciente);

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
