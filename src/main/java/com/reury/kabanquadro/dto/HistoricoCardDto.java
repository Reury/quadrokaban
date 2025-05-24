package com.reury.kabanquadro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoCardDto {
    private Long id;
    private Long cardId;
    private Long boardId;
    private String tipoEvento;
    private LocalDateTime dataHoraEvento;
    private String descricaoEvento;
    private String detalhesJson;
}
