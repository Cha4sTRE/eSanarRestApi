package cj.esanar.controller;


import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.service.HistoriaService;
import cj.esanar.service.PacienteService;
import cj.esanar.service.implement.security.CustomUserDetailsService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final PacienteService pacienteServiceImpl;
    private final HistoriaService historiaServiceImpl;
    private UserDetailsService userDetailsService;

    @GetMapping("/")
    public String home(Model model,@RequestParam(name = "page",defaultValue ="0") int page,@RequestParam(name = "filtro",defaultValue = "all")String filtro) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").ascending());
        Page<PatientEntity> pacientes;
        pacientes= (filtro.equals("all") ? pacienteServiceImpl.listPatients(pageable): pacienteServiceImpl.listPatients(pageable,filtro));
        PageRender<PatientEntity> pacientesRender= new PageRender<>("/enf/",pacientes);

        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService userAuth= (CustomUserDetailsService) auth.getPrincipal();

        LocalDateTime time= LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm dd/MM/yyyy");
        model.addAttribute("page",pacientesRender);
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("userAuth", userAuth);
        model.addAttribute("time", formatter.format(time));
        return "enf/home";
    }

    @GetMapping("paciente/nuevo")
    public String nuevo(PatientEntity paciente, Model model) {
        model.addAttribute("paciente", paciente);
        return "enf/paciente-form";
    }

    @GetMapping("paciente/{nombre}")
    public String paciente(PatientEntity paciente, @RequestParam Long historia, Model model, @PathVariable String nombre) {

        HistoryEntity historiaEspecifica= historiaServiceImpl.findHistoryById(historia);
        DateTimeFormatter formato=DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return "enf/paciente-form";
    }

    @GetMapping("paciente/eliminar")
    public String eliminar(PatientEntity paciente) {
        pacienteServiceImpl.deletePatients(paciente);
        return "redirect:/enf/";
    }

    @PostMapping("paciente/guardar")
    public String guardar(@Valid PatientEntity paciente, @RequestParam(value = "idHistoria",required = false) Long idHistoria, @RequestParam String fechaNacimiento, Errors errors) {
        if (errors.hasErrors()) {
            return "enf/paciente-form";
        }
        LocalDate dateNacimiento= LocalDate.parse(fechaNacimiento);
        LocalDate hoy=LocalDate.now();
        paciente.setBirthDate(dateNacimiento);
        if (paciente.getId() == null) {
            // CASO NUEVO
            HistoryEntity historiaNueva= new HistoryEntity(null, hoy, paciente, Collections.emptySet());
            historiaNueva.setPatient(paciente);
            paciente.setHistoriaEntity(historiaNueva);
        } else {
            // CASO EDICIÓN
            HistoryEntity historiaExistente = historiaServiceImpl.findHistoryById(idHistoria);
            paciente.setHistoriaEntity(historiaExistente);
        }
        pacienteServiceImpl.savePatients(paciente);

        return "redirect:/enf/";
    }

    @GetMapping("paciente/ExportarExcel")
    public void exportarExcel(HttpServletResponse response) throws IOException {

        List<PatientEntity> pacientes= pacienteServiceImpl.listPatients();

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
