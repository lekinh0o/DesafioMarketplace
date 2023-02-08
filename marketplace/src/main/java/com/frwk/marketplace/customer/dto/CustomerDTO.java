package com.frwk.marketplace.customer.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO implements Serializable {

    @JsonProperty("nome")
    @NotBlank(message = "O campo 'nome' é obrigatório")
    @Length(min = 3, message = "O nome do cliente deve conter pelo menos 3 caracteres")
    private String nome;

    @JsonProperty("cpf")
    @NotBlank(message = "O campo 'cpf' é obrigatório")
    @Length(min = 11, max = 11, message = "O cpf do cliente deve conter 11 caracteres")
    private String cpf;

    @JsonProperty("dataNascimento")
    @NotNull(message = "O campo 'dataNascimento' é obrigatório")
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "en-US")
    private LocalDate dataNascimento;

    @JsonProperty("email")
    @Email(message = "O campo 'email' é obrigatório")
    @NotBlank(message = "E-mail não poder ser null")
    private String email;

}
