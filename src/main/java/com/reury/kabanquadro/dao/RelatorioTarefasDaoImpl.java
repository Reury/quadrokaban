package com.reury.kabanquadro.dao;

import com.reury.kabanquadro.dto.RelatorioTarefasDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RelatorioTarefasDaoImpl implements RelatorioTarefasDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RelatorioTarefasDto> listarRelatorioPorBoard(Long boardId) {
        return entityManager.createQuery(
            "SELECT new com.reury.kabanquadro.dto.RelatorioTarefasDto(" +
            "c.id, c.coluna.board.id, c.id, c.dataCriacao, 0, false, '') " +
            "FROM Card c WHERE c.coluna.board.id = :boardId", RelatorioTarefasDto.class)
            .setParameter("boardId", boardId)
            .getResultList();
    }
}