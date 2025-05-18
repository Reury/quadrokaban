package com.reury.kabanquadro.service;

import com.reury.kabanquadro.model.Card;
import com.reury.kabanquadro.model.HistoricoCard;
import com.reury.kabanquadro.repository.HistoricoCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoService {

    @Autowired
    private HistoricoCardRepository historicoCardRepository;

    // Retorna o histórico detalhado de um card
    public List<HistoricoCard> historicoPorCard(Card card) {
        return historicoCardRepository.findByCardOrderByDataHoraEventoAsc(card);
    }
}
