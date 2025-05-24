package com.reury.kabanquadro.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloqueioDto {
    private Long id;
    private Long cardId; // Use o ID do card, não a entidade Card
    private LocalDateTime dataHoraBloqueio;
    private LocalDateTime dataHoraDesbloqueio;
    private String motivoBloqueio;
    private String motivoDesbloqueio;
    private int tempoBloqueado;
}
