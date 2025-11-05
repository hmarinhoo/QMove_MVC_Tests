package br.com.fiap.QMove_MVC.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Moto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String qrcode;

    @NotBlank
    private String placa;

    @NotBlank
    private String modelo;

    @NotBlank
    private String status;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor setor;
}

