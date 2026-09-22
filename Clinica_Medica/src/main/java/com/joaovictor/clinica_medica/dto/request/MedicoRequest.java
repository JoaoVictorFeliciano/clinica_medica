package com.joaovictor.clinica_medica.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MedicoRequest {

    private String nome;
    private String crm;
    private String especialidade;
}