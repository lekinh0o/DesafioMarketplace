package com.frwk.marketplace.customer.mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.frwk.marketplace.core.exceptions.InvalidClientException;
import com.frwk.marketplace.core.shared.ParserMapDTO;
import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.customer.model.Customer;

@Component
public class CustomerMapper implements ParserMapDTO<CustomerDTO, Customer> {
    @Override
    public CustomerDTO mapEntityFromDTO(Customer customer) {
        return CustomerDTO.builder().cpf(customer.getIdentificationCode())
                .nome(customer.getName())
                .dataNascimento(this.getLocaldateToString(customer.getBirthDate()))
                .email(customer.getEmail()).build();
    }

    @Override
    public Customer mapDTOFromEntity(CustomerDTO dto) throws InvalidClientException {
        return Customer.builder()
                .identificationCode(dto.getCpf())
                .name(dto.getNome())
                .birthDate(
                        this.getStringToLocaldate(dto.getDataNascimento()))
                .email(dto.getEmail())
                .build();
    }
  
    private LocalDate getStringToLocaldate(String String) throws InvalidClientException {
        try {
            LocalDate dataNacimento = LocalDate.parse(String, DateTimeFormatter.ISO_DATE);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dataNacimento.format(formatter);
            return dataNacimento;    
        } catch (Exception e) {
            throw new InvalidClientException("O campo 'dataNascimeto' deve estar no formato yyyy-MM-dd ");
        }
        
    }

    private String  getLocaldateToString( LocalDate date){
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         String dateString = date.format(formatter);
    
         return  dateString;
    }

}
