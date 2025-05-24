package com.reury.kabanquadro.dao;

import com.reury.kabanquadro.dto.HistoricoCardDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoricoCardDaoImpl implements HistoricoCardDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<HistoricoCardDto> listarHistoricoPorCard(Long cardId) {
        return entityManager.createQuery(
            "SELECT new com.reury.kabanquadro.dto.HistoricoCardDto(" +
            "h.id, h.card.id, h.board.id, h.tipoEvento, h.dataHoraEvento, h.descricaoEvento, h.detalhesJson) " +
            "FROM HistoricoCard h WHERE h.card.id = :cardId ORDER BY h.dataHoraEvento ASC", HistoricoCardDto.class)
            .setParameter("cardId", cardId)
            .getResultList();
    }
}