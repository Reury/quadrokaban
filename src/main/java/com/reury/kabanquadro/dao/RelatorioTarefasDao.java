package com.reury.kabanquadro.dao;

import com.reury.kabanquadro.dto.RelatorioTarefasDto;
import java.util.List;

public interface RelatorioTarefasDao {
    List<RelatorioTarefasDto> listarRelatorioPorBoard(Long boardId);
}