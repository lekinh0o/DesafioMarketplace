package com.frwk.marketplace.product.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    @NotBlank(message = "O campo 'nome' é obrigatório")
    @Length(min = 1, message = "O nome do cliente deve conter pelo menos 1 caracteres")
    private String name;

    @JsonProperty("preco")
    @NotNull(message = "O campo 'preço' é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "O campo 'preço' inválido")
    private Double price;

}
