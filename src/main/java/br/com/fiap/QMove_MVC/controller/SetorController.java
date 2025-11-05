package br.com.fiap.QMove_MVC.controller;

import br.com.fiap.QMove_MVC.service.SetorService;
import jakarta.validation.Valid;
import br.com.fiap.QMove_MVC.model.Setor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/setores")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @GetMapping
    public String listar(Model model) {
        var setores = setorService.listarTodos();
        model.addAttribute("setores", setores);

        // Calcula a quantidade de motos por setor e adiciona ao modelo (map setorId -> count)
        java.util.Map<Long, Long> setorCounts = new java.util.HashMap<>();
        for (var s : setores) {
            setorCounts.put(s.getId(), setorService.contarMotosPorSetorId(s.getId()));
        }
        model.addAttribute("setorCounts", setorCounts);
        return "setores/listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("setor", new Setor());
        return "setores/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Setor setor,
                        BindingResult result) {
        if (result.hasErrors()) {
            return "setores/form";
        }
        setorService.salvar(setor);
        return "redirect:/setores";
}


    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        setorService.buscarPorId(id).ifPresent(s -> model.addAttribute("setor", s));
        return "setores/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            setorService.excluir(id);
            redirectAttributes.addFlashAttribute("success", "Setor excluído com sucesso.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/setores";
    }
}