package com.reury.kabanquadro.service;

import com.reury.kabanquadro.model.*;
import com.reury.kabanquadro.repository.CardRepository;
import com.reury.kabanquadro.repository.ColunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ColunaRepository colunaRepository;

    // Cria um novo card na coluna INICIAL do board
    public Card criarCard(String titulo, String descricao, Coluna colunaInicial) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título do card é obrigatório.");
        }
        if (colunaInicial == null || colunaInicial.getTipo() != TipoColuna.INICIAL) {
            throw new IllegalArgumentException("Card deve ser criado na coluna INICIAL.");
        }

        Card card = new Card();
        card.setTitulo(titulo);
        card.setDescricao(descricao);
        card.setDataCriacao(LocalDateTime.now());
        card.setBloqueado(false);
        card.setColuna(colunaInicial);
        card.setUltimaColunaId(colunaInicial.getId());
        card.setDataEntradaColuna(LocalDateTime.now());

        return cardRepository.save(card);
    }

    // Move o card para a próxima coluna (respeitando regras)
    public Card moverParaProximaColuna(Card card) {
        if (card.isBloqueado()) {
            throw new IllegalStateException("Card está bloqueado e não pode ser movido.");
        }

        Coluna colunaAtual = card.getColuna();
        Board board = colunaAtual.getBoard();

        int ordemAtual = colunaAtual.getOrdem();
        Optional<Coluna> proximaColuna = board.getColunas().stream()
                .filter(c -> c.getOrdem() == ordemAtual + 1)
                .findFirst();

        if (proximaColuna.isEmpty()) {
            throw new IllegalStateException("Não existe próxima coluna para mover.");
        }

  
        card.setUltimaColunaId(colunaAtual.getId());
        card.setColuna(proximaColuna.get());
        card.setDataEntradaColuna(LocalDateTime.now());

        return cardRepository.save(card);
    }

    // Cancela o card (move para coluna de cancelamento)
    public Card cancelarCard(Card card) {
        if (card.isBloqueado()) {
            throw new IllegalStateException("Card está bloqueado e não pode ser cancelado.");
        }

        Board board = card.getColuna().getBoard();
        Optional<Coluna> colunaCancelamento = board.getColunas().stream()
                .filter(c -> c.getTipo() == TipoColuna.CANCELAMENTO)
                .findFirst();

        if (colunaCancelamento.isEmpty()) {
            throw new IllegalStateException("Coluna de cancelamento não encontrada.");
        }

        card.setUltimaColunaId(card.getColuna().getId());
        card.setColuna(colunaCancelamento.get());
        card.setDataEntradaColuna(LocalDateTime.now());

        return cardRepository.save(card);
    }

    // Bloqueia o card
    public Card bloquearCard(Card card, String motivo) {
        if (card.isBloqueado()) {
            throw new IllegalStateException("Card já está bloqueado.");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Motivo do bloqueio é obrigatório.");
        }
        card.setBloqueado(true);
        // Aqui você pode criar um registro de Bloqueio também
        return cardRepository.save(card);
    }

    // Desbloqueia o card
    public Card desbloquearCard(Card card, String motivo) {
        if (!card.isBloqueado()) {
            throw new IllegalStateException("Card não está bloqueado.");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Motivo do desbloqueio é obrigatório.");
        }
        card.setBloqueado(false);
        // Aqui você pode atualizar o registro de Bloqueio também
        return cardRepository.save(card);
    }

    public Card buscarPorId(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Card não encontrado!"));
    }
}
