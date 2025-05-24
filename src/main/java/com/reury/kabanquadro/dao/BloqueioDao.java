package com.reury.kabanquadro.dao;

import com.reury.kabanquadro.dto.BloqueioDto;
import java.util.List;

public interface BloqueioDao {
    List<BloqueioDto> listarBloqueiosPorBoard(Long boardId);
}