package com.reury.kabanquadro.dao;

import com.reury.kabanquadro.dto.BloqueioDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BloqueioDaoImpl implements BloqueioDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BloqueioDto> listarBloqueiosPorBoard(Long boardId) {
        return entityManager.createQuery(
            "SELECT new com.reury.kabanquadro.dto.BloqueioDto(" +
            "b.id, b.card.id, b.dataHoraBloqueio, b.dataHoraDesbloqueio, " +
            "b.motivoBloqueio, b.motivoDesbloqueio, b.tempoBloqueado) " +
            "FROM Bloqueio b WHERE b.card.coluna.board.id = :boardId", BloqueioDto.class)
            .setParameter("boardId", boardId)
            .getResultList();
    }
}