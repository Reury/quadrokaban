package com.reury.kabanquadro.service;

import com.reury.kabanquadro.model.*;
import com.reury.kabanquadro.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private BloqueioRepository bloqueioRepository;

    // Tempo total do card
    public long tempoTotalCard(Card card) {
        return movimentacaoRepository.findByCard(card)
            .stream()
            .mapToLong(mov -> mov.getTempoNaColuna().getSeconds())
            .sum();
    }

    // Tempo por coluna
    public Map<Long, Long> tempoPorColuna(Card card) {
        return movimentacaoRepository.findByCard(card)
            .stream()
            .collect(Collectors.groupingBy(
                Movimentacao::getColunaOrigemId,
                Collectors.summingLong(mov -> mov.getTempoNaColuna().getSeconds())
            ));
    }

    // Quantidade de bloqueios
    public int quantidadeBloqueios(Card card) {
        return (int) bloqueioRepository.findAll().stream()
            .filter(b -> b.getCard().equals(card))
            .count();
    }

    // Tempo total bloqueado
    public int tempoTotalBloqueado(Card card) {
        return bloqueioRepository.findAll().stream()
            .filter(b -> b.getCard().equals(card))
            .mapToInt(Bloqueio::getTempoBloqueado)
            .sum();
    }

    // Resumo geral do board
    public Map<TipoColuna, Long> resumoCardsPorTipoColuna(Board board) {
        return board.getColunas().stream()
            .collect(Collectors.toMap(
                Coluna::getTipo,
                coluna -> coluna.getCards() == null ? 0L : (long) coluna.getCards().size()
            ));
    }
}
