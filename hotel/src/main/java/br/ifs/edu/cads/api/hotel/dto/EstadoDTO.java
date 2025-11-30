package br.ifs.edu.cads.api.hotel.dto;

import jakarta.validation.constraints.NotBlank;

public record EstadoDTO ( 

    Long id,

    @NotBlank (message = "Unidade Federal é obrigatória.")
    String uf
) {}
