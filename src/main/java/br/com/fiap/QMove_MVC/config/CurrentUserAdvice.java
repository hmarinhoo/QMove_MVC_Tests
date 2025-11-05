package br.com.fiap.QMove_MVC.config;

import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import br.com.fiap.QMove_MVC.repository.FuncionarioRepository;

@ControllerAdvice
public class CurrentUserAdvice {

    private final FuncionarioRepository funcionarioRepository;

    public CurrentUserAdvice(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @ModelAttribute
    public void addCurrentUser(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();
            funcionarioRepository.findByEmail(email).ifPresent(func -> {
                model.addAttribute("currentUserName", func.getNome());
                model.addAttribute("currentUserEmail", func.getEmail());
                model.addAttribute("currentUserRoles", func.getRoles().stream()
                        .map(r -> r.getNome().replace("ROLE_", ""))
                        .collect(Collectors.toList()));
            });
        }
    }
}
