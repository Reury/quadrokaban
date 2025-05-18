package com.reury.kabanquadro.service;

import com.reury.kabanquadro.model.*;
import com.reury.kabanquadro.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestaoService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ColunaRepository colunaRepository;

    // Excluir um board (só se não tiver cards ativos)
    public void excluirBoard(Board board) {
        boolean temCards = board.getColunas().stream()
                .anyMatch(coluna -> coluna.getCards() != null && !coluna.getCards().isEmpty());
        if (temCards) {
            throw new IllegalStateException("Não é possível excluir um board com cards ativos.");
        }
        boardRepository.delete(board);
    }

    // Arquivar um board (marca como inativo)
    public void arquivarBoard(Board board) {
        board.setAtivo(false);
        boardRepository.save(board);
    }

    // Excluir um card
    public void excluirCard(Card card) {
        cardRepository.delete(card);
    }

    // Arquivar um card (pode ser um campo booleano, ex: arquivado)
    public void arquivarCard(Card card) {
        card.setBloqueado(true); // ou crie um campo 'arquivado' se preferir
        cardRepository.save(card);
    }

    // Excluir uma coluna (só se não tiver cards)
    public void excluirColuna(Coluna coluna) {
        if (coluna.getTipo() == TipoColuna.INICIAL ||
            coluna.getTipo() == TipoColuna.FINAL ||
            coluna.getTipo() == TipoColuna.CANCELAMENTO) {
            throw new IllegalArgumentException("Não é permitido excluir colunas obrigatórias.");
        }
        colunaRepository.delete(coluna);
    }
}
