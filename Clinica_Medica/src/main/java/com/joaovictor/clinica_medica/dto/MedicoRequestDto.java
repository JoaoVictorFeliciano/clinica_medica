package com.joaovictor.clinica_medica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicoRequestDto {

    private String nome;
    private String crm;
    private String especialidade;
}