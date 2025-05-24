package com.reury.kabanquadro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioTarefasDto {
    private Long id;
    private Long boardId;
    private Long cardId;
    private LocalDateTime dataCriacao;
    private int tempoTotalNasTarefas;
    private boolean concluido;
    private String tempoDetalhesJson;
}
