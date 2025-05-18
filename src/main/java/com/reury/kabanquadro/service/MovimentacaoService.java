package com.reury.kabanquadro.service;

import com.reury.kabanquadro.model.*;
import com.reury.kabanquadro.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private HistoricoCardRepository historicoCardRepository;

    // Move o card para a coluna destino, validando regras
    public Movimentacao moverCardParaColuna(Card card, Coluna colunaDestino) {
        if (card.isBloqueado()) {
            throw new IllegalStateException("Card está bloqueado e não pode ser movido.");
        }

        Coluna colunaOrigem = card.getColuna();
        Board board = colunaOrigem.getBoard();
        LocalDateTime agora = LocalDateTime.now();

        // Regra: só pode mover para a próxima coluna na ordem, exceto para cancelamento
        if (colunaDestino.getTipo() != TipoColuna.CANCELAMENTO) {
            int ordemEsperada = colunaOrigem.getOrdem() + 1;
            if (colunaDestino.getOrdem() != ordemEsperada) {
                throw new IllegalArgumentException("Só é permitido mover para a próxima coluna na ordem.");
            }
        } else {
            // Só pode cancelar se não estiver na coluna FINAL
            if (colunaOrigem.getTipo() == TipoColuna.FINAL) {
                throw new IllegalArgumentException("Não é permitido cancelar um card já finalizado.");
            }
        }

        // Calcula o tempo na coluna atual
        Duration tempoNaColuna = Duration.between(card.getDataEntradaColuna(), agora);

        // Cria registro de movimentação
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setCard(card);
        movimentacao.setColunaOrigemId(colunaOrigem.getId());
        movimentacao.setColunaDestinoId(colunaDestino.getId());
        movimentacao.setDataHoraEntrada(card.getDataEntradaColuna());
        movimentacao.setDataHoraSaida(agora);
        movimentacao.setTempoNaColuna(tempoNaColuna);
        movimentacaoRepository.save(movimentacao);

        // Atualiza o card para a nova coluna
        card.setUltimaColunaId(colunaOrigem.getId());
        card.setColuna(colunaDestino);
        card.setDataEntradaColuna(agora);
        cardRepository.save(card);

        // Registra histórico
        HistoricoCard historico = new HistoricoCard();
        historico.setCard(card);
        historico.setBoard(board);
        historico.setTipoEvento(TipoEvento.MOVIMENTACAO);
        historico.setDataHoraEvento(agora);
        historico.setDescricaoEvento("Card movido de '" + colunaOrigem.getNome() + "' para '" + colunaDestino.getNome() + "'");
        historico.setDetalhesJson(null); // Pode adicionar detalhes extras em JSON se quiser
        historicoCardRepository.save(historico);

        return movimentacao;
    }

    public Movimentacao cancelarCard(Card card, Coluna colunaCancelamento, String motivoCancelamento) {
        if (card.isBloqueado()) {
            throw new IllegalStateException("Card está bloqueado e não pode ser cancelado.");
        }

        Coluna colunaOrigem = card.getColuna();
        Board board = colunaOrigem.getBoard();

        if (colunaOrigem.getTipo() == TipoColuna.FINAL) {
            throw new IllegalArgumentException("Não é permitido cancelar um card já finalizado.");
        }
        if (colunaCancelamento.getTipo() != TipoColuna.CANCELAMENTO) {
            throw new IllegalArgumentException("Coluna de destino não é de cancelamento.");
        }

        LocalDateTime agora = LocalDateTime.now();
        Duration tempoNaColuna = Duration.between(card.getDataEntradaColuna(), agora);

        // Cria registro de movimentação
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setCard(card);
        movimentacao.setColunaOrigemId(colunaOrigem.getId());
        movimentacao.setColunaDestinoId(colunaCancelamento.getId());
        movimentacao.setDataHoraEntrada(card.getDataEntradaColuna());
        movimentacao.setDataHoraSaida(agora);
        movimentacao.setTempoNaColuna(tempoNaColuna);
        movimentacaoRepository.save(movimentacao);

        // Atualiza o card para a coluna de cancelamento
        card.setUltimaColunaId(colunaOrigem.getId());
        card.setColuna(colunaCancelamento);
        card.setDataEntradaColuna(agora);
        cardRepository.save(card);

        // Registra histórico
        HistoricoCard historico = new HistoricoCard();
        historico.setCard(card);
        historico.setBoard(board);
        historico.setTipoEvento(TipoEvento.MOVIMENTACAO);
        historico.setDataHoraEvento(agora);
        historico.setDescricaoEvento("Card cancelado na coluna '" + colunaCancelamento.getNome() + "'. Motivo: " + motivoCancelamento);
        historico.setDetalhesJson(null); // Pode adicionar detalhes extras em JSON se quiser
        historicoCardRepository.save(historico);

        return movimentacao;
    }
}
