package com.reury.kabanquadro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioBloqueiosDto {
    private Long id;
    private Long boardId;
    private Long cardId;
    private int quantidadeBloqueios;
    private int tempoTotalBloqueado;
    private String motivosBloqueioJson;
    private String motivosDesbloqueioJson;
}
