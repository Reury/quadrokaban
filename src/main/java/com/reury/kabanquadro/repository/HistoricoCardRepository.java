package com.reury.kabanquadro.repository;

import com.reury.kabanquadro.model.Card;
import com.reury.kabanquadro.model.HistoricoCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoricoCardRepository extends JpaRepository<HistoricoCard, Long> {
    List<HistoricoCard> findByCardOrderByDataHoraEventoAsc(Card card);
}