package cj.esanar.controller;


import cj.esanar.persistence.entity.auth.UserEntity;
import cj.esanar.persistence.repository.PermissionsRepository;
import cj.esanar.persistence.repository.RoleRepository;
import cj.esanar.service.implement.security.CustomUserDetailsService;
import cj.esanar.service.implement.security.UserDetailServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class AdminController {


    private UserDetailServiceImpl userDetailService;
    private RoleRepository roleRepository;
    private PermissionsRepository permissionsRepository;

    @GetMapping("/")
    public String dashboar(Model model) {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService usuarioAuth= (CustomUserDetailsService) auth.getPrincipal();
        List<UserEntity> users= userDetailService.getAllUsers();
        model.addAttribute("usuarioAuth", usuarioAuth);
        model.addAttribute("users", users);
        model.addAttribute("listaRoles", roleRepository.findAll());

        return "admin/dashboard";
    }
    @ModelAttribute("usuario")
    public UserEntity usuario() {
        return new UserEntity();
    }

    @GetMapping("/nuevoRegistro")
    public String nuevoRegistro(Model model) {
        model.addAttribute("listaRoles", roleRepository.findAll());
        return "admin/registro";
    }


    @PostMapping("/guardarRegistro")
    public String guardarRegistro(@Valid @ModelAttribute("usuario") UserEntity usuario, Errors errors){
        if(errors.hasErrors()){
            System.out.println("Errores en la validación: " + errors.getAllErrors());
            return "admin/registro";
        }

        usuario.setEnabled(true);
        usuario.setAccountNonLocked(true);
        usuario.setAccountNonExpired(true);
        usuario.setCredentialsNonExpired(true);


        userDetailService.saveUser(usuario);
        return "redirect:/admin/";
    }

    @PostMapping("/isEnable")
    @ResponseBody
    public String isEnable(@RequestParam("id") Long id, @RequestParam("enabled") boolean enabled, HttpServletRequest request) {
        UserEntity user = userDetailService.getById(id);
        System.out.println("el id es: " + id);
        if (user != null) {
            user.setEnabled(enabled);
            userDetailService.saveUser(user);
            return "Usuario actualizado con éxito.";
        }


        return "Error: usuario no encontrado.";
    }




}
