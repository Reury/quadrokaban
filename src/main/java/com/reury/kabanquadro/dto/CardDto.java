package com.reury.kabanquadro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private boolean bloqueado;
    private boolean arquivado;
    private Long colunaId;
    private Long ultimaColunaId;
    private LocalDateTime dataEntradaColuna;
}
