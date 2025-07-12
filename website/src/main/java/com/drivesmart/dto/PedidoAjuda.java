package com.drivesmart.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PedidoAjuda {

    @NotBlank
    private String nome;

    @Email @NotBlank
    private String email;

    @NotBlank
    private String mensagem;
}
