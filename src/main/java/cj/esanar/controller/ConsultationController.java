package cj.esanar.controller;

import cj.esanar.persistence.entity.ConsultaEntity;
import cj.esanar.persistence.entity.HistoriaEntity;
import cj.esanar.persistence.entity.auth.UserEntity;
import cj.esanar.service.ConsultationService;
import cj.esanar.service.HistoryService;
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
@RequestMapping("/consult")
@PreAuthorize("hasAnyRole('ENF','ADMIN','MEDIC')")
public class ConsultationController {

    private final ConsultationService consultationService;
    private final HistoryService historyService;
    private final UserDetailServiceImpl userDetailService;


    @GetMapping("/historias/{nombre}")
    public String historias(Model model,HistoriaEntity historia,@RequestParam(name = "page",defaultValue = "0")int page,
                            @PathVariable String nombre, @RequestParam(name = "filtro",defaultValue = "all")String filtro) {


        DateTimeFormatter formato= DateTimeFormatter.ofPattern("yyyy-MM-dd");
        HistoriaEntity consultasHistoria= historyService.findHistoryById(historia.getId());
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").ascending());
        Page<ConsultaEntity> consultaPage;
        if (filtro == null || filtro.isBlank() || filtro.equals("all")) {
            consultaPage = consultationService.listConsultations(pageable, consultasHistoria.getId());
        } else {
            consultaPage = consultationService.listConsultations(pageable, consultasHistoria.getId(), filtro);
        }

        PageRender<ConsultaEntity> consultaRender= new PageRender<>("/consulta/historias/"+nombre+"?id="+historia.getId(),consultaPage);

        return "consulta/historias";
    }

    @GetMapping("/nueva")
    public String nueva(ConsultaEntity consulta,@RequestParam HistoriaEntity historiaId, Model model) {


        LocalDateTime ahora= LocalDateTime.now();
        DateTimeFormatter horaFormat= DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

        model.addAttribute("consulta", consulta);
        model.addAttribute("historiaPaciente", historyService.findHistoryById(historiaId.getId()));
        model.addAttribute("hora", ahora.format(horaFormat));
        return "consulta/consulta-form";
    }

    @GetMapping("/historia/{historiaId}")
    public String verConsulta(@PathVariable HistoriaEntity historiaId,@RequestParam ConsultaEntity consulta, Model model) {

        ConsultaEntity editConsulta= consultationService.findConsultationtById(consulta);
        HistoriaEntity editHistoria= historyService.findHistoryById(historiaId.getId());
        LocalDateTime fechaHoraAtencion= editConsulta.getFechaHoraAtencion();

        return "consulta/consulta-form";
    }

    @GetMapping("/exportarInforme")
    public void exportarInforme(@RequestParam ConsultaEntity consulta, HttpServletResponse response) throws IOException {

       response.setContentType("application/pdf");
       DateTimeFormatter formato= DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
       String fecha= formato.format(consulta.getFechaHoraAtencion());

       String cabecera= "Content-Disposition";
       String valor="attachment; filename=Consulta_"+fecha+".pdf";
       response.setHeader(cabecera,valor);
       ConsultaEntity consultapdf= consultationService.findConsultationtById(consulta);
       ExportarConsultaPdf exportar= new ExportarConsultaPdf(consultapdf);
       exportar.export(response);
    }

    @PostMapping("/agregar")
    public String agregar(@Valid ConsultaEntity consulta, @RequestParam("idHistoria") Long idHistoria, @RequestParam LocalDateTime fechaHora, Errors errors) {
        if(errors.hasErrors()) {
            System.out.println(errors.getAllErrors());
            return "consulta/consulta-form";
        }
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService enfUser= (CustomUserDetailsService) auth.getPrincipal();
        UserEntity enf= userDetailService.getById(enfUser.getId());

        consulta.setEnfermera(enf);
        consulta.setFechaHoraAtencion(fechaHora);
        consulta.setHoraFinal(LocalTime.now());

        HistoriaEntity hPaciente= historyService.findHistoryById(idHistoria);
        consulta.setHistoriaClinica(hPaciente);
        hPaciente.agregarConsultas(consulta);

        consultationService.findConsultationtById(consulta);
        return "redirect:/consulta/historias/"+hPaciente.getPaciente().getNombre()+"?"+"id="+hPaciente.getId();
    }


}
