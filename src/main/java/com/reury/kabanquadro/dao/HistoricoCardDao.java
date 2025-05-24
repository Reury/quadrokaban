package com.reury.kabanquadro.dao;

import com.reury.kabanquadro.dto.HistoricoCardDto;
import java.util.List;

public interface HistoricoCardDao {
    List<HistoricoCardDto> listarHistoricoPorCard(Long cardId);
}