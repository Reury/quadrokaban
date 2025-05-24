package com.reury.kabanquadro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoDto {
    private Long id;
    private Long cardId;
    private Long colunaOrigemId;
    private Long colunaDestinoId;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private Duration tempoNaColuna;
}
