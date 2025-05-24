package com.reury.kabanquadro.dao;

import com.reury.kabanquadro.dto.RelatorioBloqueiosDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RelatorioBloqueiosDaoImpl implements RelatorioBloqueiosDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RelatorioBloqueiosDto> listarRelatorioPorBoard(Long boardId) {
        return entityManager.createQuery(
            "SELECT new com.reury.kabanquadro.dto.RelatorioBloqueiosDto(" +
            "b.id, b.card.coluna.board.id, b.card.id, COUNT(b), SUM(b.tempoBloqueado), '', '') " +
            "FROM Bloqueio b WHERE b.card.coluna.board.id = :boardId GROUP BY b.card.id", RelatorioBloqueiosDto.class)
            .setParameter("boardId", boardId)
            .getResultList();
    }
}