package com.joaovictor.clinica_medica.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultaRequest {

    private Long pacienteId;
    private Long medicoId;
    private LocalDateTime dataHora;
}