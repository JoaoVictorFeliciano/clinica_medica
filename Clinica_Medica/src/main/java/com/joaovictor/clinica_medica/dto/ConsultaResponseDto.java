package com.joaovictor.clinica_medica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaResponseDto {

    private Long id;
    private LocalDateTime dataHora;
    private String descricao;
    private Long medicoId;
    private Long pacienteId;
}