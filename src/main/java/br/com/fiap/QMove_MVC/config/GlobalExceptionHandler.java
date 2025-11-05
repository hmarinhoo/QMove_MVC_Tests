package br.com.fiap.QMove_MVC.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrity(DataIntegrityViolationException ex) {
        String friendly = null;
        // Tenta identificar constraint de placa duplicada
        Throwable root = ex.getRootCause();
        String msg = ex.getMessage();
        if (root instanceof SQLException) {
            msg = root.getMessage();
        }

        if (msg != null && msg.toLowerCase().contains("moto_placa_key")) {
            friendly = "Já existe uma moto com essa placa cadastrada. Não é permitido ter duas motos com a mesma placa.";
        }

        ModelAndView mv = new ModelAndView("error");
        mv.addObject("status", 500);
        mv.addObject("message", friendly != null ? friendly : "Erro de integridade de dados.");
        return mv;
    }

    // Fallback: outras exceções podem continuar para o handler padrão ou você pode adicionar mais handlers aqui
}
