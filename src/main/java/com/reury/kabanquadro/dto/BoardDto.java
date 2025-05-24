package com.reury.kabanquadro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;
    private String nome;
    private LocalDateTime dataCriacao;
    private boolean ativo;
    private boolean arquivado;
    private Set<ColunaDto> colunas;
}
