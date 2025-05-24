package com.reury.kabanquadro.dao;

import com.reury.kabanquadro.dto.RelatorioBloqueiosDto;
import java.util.List;

public interface RelatorioBloqueiosDao {
    List<RelatorioBloqueiosDto> listarRelatorioPorBoard(Long boardId);
}