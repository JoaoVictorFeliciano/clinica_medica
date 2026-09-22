package com.joaovictor.clinica_medica.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PacienteRequest {

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
}