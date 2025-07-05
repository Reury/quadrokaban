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

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
    // @Autowired
    // private ColunaRepository colunaRepository;

    // Cria um novo card na coluna INICIAL do board
    public Card criarCard(String titulo, String descricao, Coluna colunaInicial) {
        // Validações de título e descrição
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título do card é obrigatório.");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição do card é obrigatória.");
        }

        // Verifica se a coluna inicial é válida
        if (colunaInicial == null || colunaInicial.getTipo() != TipoColuna.INICIAL) {
            throw new IllegalArgumentException("Card deve ser criado na coluna INICIAL.");
        }

        // Impedir criação de cards duplicados no mesmo board
        boolean cardDuplicado = colunaInicial.getBoard().getColunas().stream()
            .flatMap(coluna -> coluna.getCards().stream())
            .anyMatch(card -> card.getTitulo().equalsIgnoreCase(titulo));
        if (cardDuplicado) {
            throw new IllegalArgumentException("Já existe um card com esse título no board.");
        }

        // Criação do card
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
        // Verifica se o card está bloqueado
        if (card.isBloqueado()) {
            throw new IllegalStateException("Card está bloqueado e não pode ser movido.");
        }

        // Verifica se a coluna atual é válida
        Coluna colunaAtual = card.getColuna();
        if (colunaAtual.getTipo() == TipoColuna.FINAL) {
            throw new IllegalStateException("Card já está na coluna FINAL e não pode ser movido.");
        }

        // Busca a próxima coluna na ordem
        Board board = colunaAtual.getBoard();
        int ordemAtual = colunaAtual.getOrdem();
        Optional<Coluna> proximaColuna = board.getColunas().stream()
            .filter(c -> c.getOrdem() == ordemAtual + 1)
            .findFirst();

        if (proximaColuna.isEmpty()) {
            throw new IllegalStateException("Não existe próxima coluna para mover.");
        }

        // Move o card para a próxima coluna
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
        // Verifica se o card já está na coluna de cancelamento
        if (card.getColuna().getTipo() == TipoColuna.CANCELAMENTO) {
            throw new IllegalStateException("Card já está na coluna de cancelamento.");
        }

        // Verifica se o card está na coluna FINAL
        if (card.getColuna().getTipo() == TipoColuna.FINAL) {
            throw new IllegalStateException("Não é permitido cancelar um card que já está na coluna FINAL.");
        } 

        // Busca a coluna de cancelamento
        Board board = card.getColuna().getBoard();
        Optional<Coluna> colunaCancelamento = board.getColunas().stream()
            .filter(c -> c.getTipo() == TipoColuna.CANCELAMENTO)
            .findFirst();

        if (colunaCancelamento.isEmpty()) {
            throw new IllegalStateException("Coluna de cancelamento não encontrada.");
        }

        // Move o card para a coluna de cancelamento
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
        // Verifica se o card está em uma coluna FINAL ou CANCELAMENTO
        TipoColuna tipoColuna = card.getColuna().getTipo();
        if (tipoColuna == TipoColuna.FINAL || tipoColuna == TipoColuna.CANCELAMENTO) {
            throw new IllegalStateException("Não é permitido bloquear um card em uma coluna FINAL ou CANCELAMENTO.");
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

    public void excluirCard(Card card) {
        // Verifica se o card está bloqueado
        if (card.isBloqueado()) {
            throw new IllegalStateException("Não é permitido excluir um card bloqueado.");
        }

        // Verifica se o card está na coluna FINAL
        if (card.getColuna().getTipo() == TipoColuna.FINAL) {
            throw new IllegalStateException("Não é permitido excluir um card que está na coluna FINAL.");
        }

        // Excluir card
        cardRepository.delete(card);
    }
}
