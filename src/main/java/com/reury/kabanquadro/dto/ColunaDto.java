package com.reury.kabanquadro.dto;

import java.util.Set;

import com.reury.kabanquadro.model.TipoColuna;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColunaDto {   
    private Long id;
    private String nome;
    private TipoColuna tipo;
    private Long boardId;
    private Set<CardDto> cards;
    private boolean arquivado;
    private int ordem;
    private boolean bloqueado;
   
}
