package br.com.fiap.QMove_MVC.controller;

import br.com.fiap.QMove_MVC.model.Moto;
import br.com.fiap.QMove_MVC.repository.MotoRepository;
import br.com.fiap.QMove_MVC.repository.SetorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private SetorRepository setorRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("motos", motoRepository.findAll());
        return "motos/listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("moto", new Moto());
        model.addAttribute("setores", setorRepository.findAll());
        return "motos/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
    // agora buscamos por qrcode (identificador visível) em vez de id nas rotas públicas
    // o endpoint antigo por id é substituído por /editar/{qrcode}
    Moto moto = motoRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Moto inválida"));
    model.addAttribute("moto", moto);
    model.addAttribute("setores", setorRepository.findAll());
    return "motos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Moto moto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("setores", setorRepository.findAll());
            return "motos/form";
        }
        // Se for edição, preserva o qrcode existente (form não envia esse campo)
        if (moto.getId() != null) {
            motoRepository.findById(moto.getId()).ifPresent(existing -> moto.setQrcode(existing.getQrcode()));
        }

        // Se for criação, gera um qrcode legível e único (ex: Moto01)
        if (moto.getQrcode() == null || moto.getQrcode().isBlank()) {
            moto.setQrcode(generateUniqueQrcode());
        }

        motoRepository.save(moto);
        return "redirect:/motos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        // mecanismo antigo: excluir por id. Vamos manter compatibilidade, mas também
        // fornecer rotas por qrcode na lista.
        motoRepository.deleteById(id);
        return "redirect:/motos";
    }

    // Gera um qrcode legível e curto. Tenta sequência baseada na contagem e garante unicidade.
    private String generateUniqueQrcode() {
        long base = motoRepository.count() + 1;
        int attempt = 0;
        String candidate;
        do {
            candidate = String.format("Moto%02d", base + attempt);
            attempt++;
            if (attempt > 1000) {
                candidate = "Moto" + java.util.UUID.randomUUID().toString().substring(0, 8);
                break;
            }
        } while (motoRepository.existsByQrcode(candidate));
        return candidate;
    }

    // Versões que aceitam qrcode no caminho (úteis para links públicos/QR scans)
    @GetMapping("/editar/qrcode/{qrcode}")
    public String editarPorQrcode(@PathVariable String qrcode, Model model) {
        Moto moto = motoRepository.findByQrcode(qrcode)
                .orElseThrow(() -> new IllegalArgumentException("Moto inválida"));
        model.addAttribute("moto", moto);
        model.addAttribute("setores", setorRepository.findAll());
        return "motos/form";
    }

    @GetMapping("/excluir/qrcode/{qrcode}")
    public String excluirPorQrcode(@PathVariable String qrcode) {
        Moto moto = motoRepository.findByQrcode(qrcode)
                .orElseThrow(() -> new IllegalArgumentException("Moto inválida"));
        motoRepository.delete(moto);
        return "redirect:/motos";
    }
}
