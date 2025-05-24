package com.reury.kabanquadro.service;

import com.reury.kabanquadro.dto.HistoricoCardDto;
import com.reury.kabanquadro.model.Card;
import com.reury.kabanquadro.model.HistoricoCard;
import com.reury.kabanquadro.dao.HistoricoCardDao;
import com.reury.kabanquadro.repository.HistoricoCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoService {

    @Autowired
    private HistoricoCardRepository historicoCardRepository;

    @Autowired
    private HistoricoCardDao historicoCardDao;

    // Retorna o histórico detalhado de um card
    public List<HistoricoCard> historicoPorCard(Card card) {
        return historicoCardRepository.findByCardOrderByDataHoraEventoAsc(card);
    }

    public List<HistoricoCardDto> listarHistoricoPorCard(Long cardId) {
        return historicoCardDao.listarHistoricoPorCard(cardId);
    }
}
