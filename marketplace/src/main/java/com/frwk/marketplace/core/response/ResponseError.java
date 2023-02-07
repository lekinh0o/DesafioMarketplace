package com.frwk.marketplace.core.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter@Setter@Builder
public class ResponseError  {

    private String type;
   
    private String messagem;

    @Builder.Default
    private List<ErrorObject> erros = new ArrayList<>();

}