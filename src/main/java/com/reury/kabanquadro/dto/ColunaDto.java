package com.reury.kabanquadro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColunaDto {   
    private Long id;
    private String nome;
    private Long boardId;
    private boolean arquivado;
   
}
