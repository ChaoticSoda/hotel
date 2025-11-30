package br.ifs.edu.cads.api.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CidadeDTO(
    Long id,

    @NotBlank(message = "Nome da cidade é obrigatório.")
    @Size(max = 150, message =  "Nome da cidade não pode exceder 150 caracteres.")
    String nome,

    Long estadoID 
) {
}
