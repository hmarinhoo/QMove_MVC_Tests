package br.com.fiap.QMove_MVC.controller;

import br.com.fiap.QMove_MVC.service.RoleService;
import jakarta.validation.Valid;
import br.com.fiap.QMove_MVC.service.FuncionarioService;
import br.com.fiap.QMove_MVC.model.Funcionario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    private final RoleService roleService;

    public FuncionarioController(FuncionarioService funcionarioService, RoleService roleService) {
        this.funcionarioService = funcionarioService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("funcionarios", funcionarioService.listarTodos());
        return "funcionarios/listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        model.addAttribute("roles", roleService.listarTodas());
        return "funcionarios/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Funcionario funcionario,
                        BindingResult result,
                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.listarTodas());
            return "funcionarios/form";
        }
        funcionarioService.salvar(funcionario);
        return "redirect:/funcionarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        funcionarioService.buscarPorId(id).ifPresent(f -> model.addAttribute("funcionario", f));
        model.addAttribute("roles", roleService.listarTodas());
        return "funcionarios/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        funcionarioService.excluir(id);
        return "redirect:/funcionarios";
    }
}