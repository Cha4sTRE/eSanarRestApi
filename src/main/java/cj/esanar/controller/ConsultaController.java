package cj.esanar.controller;

import cj.esanar.persistence.entity.EvaluationEntity;
import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.entity.auth.UserEntity;
import cj.esanar.service.ConsultationService;
import cj.esanar.service.HistoriaService;
import cj.esanar.service.implement.security.CustomUserDetailsService;
import cj.esanar.service.implement.security.UserDetailServiceImpl;
import cj.esanar.util.pagination.PageRender;
import cj.esanar.util.reports.ExportarConsultaPdf;
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
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor

@RestController
@RequestMapping("/consulta")
@PreAuthorize("hasAnyRole('ENF','ADMIN','MEDIC')")
public class ConsultaController {

    private final ConsultationService consultaService;
    private final HistoriaService historiaService;
    private final UserDetailServiceImpl userDetailService;


    @GetMapping("/historias/{nombre}")
    public String historias(Model model, HistoryEntity historia, @RequestParam(name = "page",defaultValue = "0")int page,
                            @PathVariable String nombre, @RequestParam(name = "filtro",defaultValue = "all")String filtro) {


        DateTimeFormatter formato= DateTimeFormatter.ofPattern("yyyy-MM-dd");
        HistoryEntity consultasHistoria= historiaService.findHistoryById(historia.getId());
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").ascending());
        Page<EvaluationEntity> consultaPage;
        if (filtro == null || filtro.isBlank() || filtro.equals("all")) {
            consultaPage = consultaService.listConsultations(pageable, consultasHistoria.getId());
        } else {
            consultaPage = consultaService.listConsultations(pageable, consultasHistoria.getId(), filtro);
        }

        PageRender<EvaluationEntity> consultaRender= new PageRender<>("/consulta/historias/"+nombre+"?id="+historia.getId(),consultaPage);

        return "consulta/historias";
    }

    @GetMapping("/nueva")
    public String nueva(EvaluationEntity consulta, @RequestParam HistoryEntity historiaId, Model model) {


        LocalDateTime ahora= LocalDateTime.now();
        DateTimeFormatter horaFormat= DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

        model.addAttribute("consulta", consulta);
        model.addAttribute("historiaPaciente",historiaService.findHistoryById(historiaId.getId()));
        model.addAttribute("hora", ahora.format(horaFormat));
        return "consulta/consulta-form";
    }

    @GetMapping("/historia/{historiaId}")
    public String verConsulta(@PathVariable HistoryEntity historiaId, @RequestParam EvaluationEntity consulta, Model model) {

        EvaluationEntity editConsulta= consultaService.findConsultationtById(consulta);
        HistoryEntity editHistoria= historiaService.findHistoryById(historiaId.getId());
        LocalDateTime fechaHoraAtencion= editConsulta.getServiceDate();

        return "consulta/consulta-form";
    }

    @GetMapping("/exportarInforme")
    public void exportarInforme(@RequestParam EvaluationEntity consulta, HttpServletResponse response) throws IOException {

       response.setContentType("application/pdf");
       DateTimeFormatter formato= DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
       String fecha= formato.format(consulta.getServiceDate());

       String cabecera= "Content-Disposition";
       String valor="attachment; filename=Consulta_"+fecha+".pdf";
       response.setHeader(cabecera,valor);
       EvaluationEntity consultapdf= consultaService.findConsultationtById(consulta);
       ExportarConsultaPdf exportar= new ExportarConsultaPdf(consultapdf);
       exportar.export(response);
    }

    @PostMapping("/agregar")
    public String agregar(@Valid EvaluationEntity consulta, @RequestParam("idHistoria") Long idHistoria, @RequestParam LocalDateTime fechaHora, Errors errors) {
        if(errors.hasErrors()) {
            System.out.println(errors.getAllErrors());
            return "consulta/consulta-form";
        }
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService enfUser= (CustomUserDetailsService) auth.getPrincipal();
        UserEntity enf= userDetailService.getById(enfUser.getId());

        consulta.setEnfermera(enf);
        consulta.setServiceDate(fechaHora);
        consulta.setFinalTime(LocalTime.now());

        HistoryEntity hPaciente= historiaService.findHistoryById(idHistoria);
        consulta.setClinicalHistory(hPaciente);
        hPaciente.agregarConsultas(consulta);

        consultaService.findConsultationtById(consulta);
        return "redirect:/consulta/historias/"+hPaciente.getPatient().getName()+"?"+"id="+hPaciente.getId();
    }


}
