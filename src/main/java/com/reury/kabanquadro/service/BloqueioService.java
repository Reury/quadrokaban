package com.reury.kabanquadro.service;

import com.reury.kabanquadro.dao.BloqueioDao;
import com.reury.kabanquadro.dto.BloqueioDto;
import com.reury.kabanquadro.model.*;
import com.reury.kabanquadro.repository.CardRepository;
import com.reury.kabanquadro.repository.HistoricoCardRepository;
import com.reury.kabanquadro.repository.BloqueioRepository;
import com.reury.kabanquadro.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BloqueioService {

    @Autowired
    private BloqueioDao bloqueioDao;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private HistoricoCardRepository historicoCardRepository;

    @Autowired
    private BloqueioRepository bloqueioRepository; // Adicione isso

    @Autowired
    private BoardRepository boardRepository;
    public BloqueioService(){
        
    }
    public BloqueioService(BloqueioRepository bloqueioRepository) {
        this.bloqueioRepository = bloqueioRepository;
    }
    public BloqueioService(BloqueioRepository bloqueioRepository,BoardRepository boardRepository) {
        this.bloqueioRepository = bloqueioRepository;
            this.boardRepository = boardRepository;

    }

    public List<BloqueioDto> listarBloqueiosPorBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board não encontrado."));

        // Verifica se o board está arquivado
        if (!board.isAtivo()) {
            throw new IllegalStateException("Não é possível gerar relatórios para boards arquivados.");
        }

        return bloqueioDao.listarBloqueiosPorBoard(boardId);
    }

    // Bloqueia um card
    public Bloqueio bloquearCard(Card card, String motivo) {
        // Verifica se o card já está bloqueado
        if (card.isBloqueado()) {
            throw new IllegalStateException("Card já está bloqueado.");
        }

        // Verifica se o motivo do bloqueio foi informado
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Motivo do bloqueio é obrigatório.");
        }

        // Verifica se o card está em uma coluna FINAL ou CANCELAMENTO
        TipoColuna tipoColuna = card.getColuna().getTipo();
        if (tipoColuna == TipoColuna.FINAL || tipoColuna == TipoColuna.CANCELAMENTO) {
            throw new IllegalStateException("Não é permitido bloquear cards em colunas FINAL ou CANCELAMENTO.");
        }

        // Marca o card como bloqueado
        card.setBloqueado(true);
        cardRepository.save(card);

        // Cria o registro de bloqueio
        Bloqueio bloqueio = new Bloqueio();
        bloqueio.setCard(card);
        bloqueio.setDataHoraBloqueio(LocalDateTime.now());
        bloqueio.setMotivoBloqueio(motivo);
        bloqueioRepository.save(bloqueio);

        // Registra o histórico do bloqueio
        HistoricoCard historico = new HistoricoCard();
        historico.setCard(card);
        historico.setBoard(card.getColuna().getBoard());
        historico.setTipoEvento(TipoEvento.BLOQUEIO);
        historico.setDataHoraEvento(LocalDateTime.now());
        historico.setDescricaoEvento("Card bloqueado. Motivo: " + motivo);
        historicoCardRepository.save(historico);

        return bloqueio;
    }

    // Desbloqueia um card
    public Bloqueio desbloquearCard(Card card, String motivo) {
        // Verifica se o motivo do desbloqueio foi informado
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Motivo do desbloqueio é obrigatório.");
        }
        // Verifica se o card está bloqueado
        if (!card.isBloqueado()) {
            throw new IllegalStateException("Card não está bloqueado.");
        }

        // Busca o último registro de bloqueio para o card
        Bloqueio bloqueio = bloqueioRepository.findTopByCardOrderByDataHoraBloqueioDesc(card)
                .orElseThrow(() -> new IllegalStateException("Nenhum bloqueio encontrado para este card."));

        // Atualiza o registro de bloqueio
        bloqueio.setDataHoraDesbloqueio(LocalDateTime.now());
        bloqueio.setMotivoDesbloqueio(motivo);
        bloqueio.setTempoBloqueado(
                (int) java.time.Duration.between(
                        bloqueio.getDataHoraBloqueio(), bloqueio.getDataHoraDesbloqueio()
                ).toSeconds()
        );
        bloqueioRepository.save(bloqueio);

        // Marca o card como desbloqueado
        card.setBloqueado(false);
        cardRepository.save(card);

        // Registra o histórico do desbloqueio
        HistoricoCard historico = new HistoricoCard();
        historico.setCard(card);
        historico.setBoard(card.getColuna().getBoard());
        historico.setTipoEvento(TipoEvento.DESBLOQUEIO);
        historico.setDataHoraEvento(LocalDateTime.now());
        historico.setDescricaoEvento("Card desbloqueado. Motivo: " + motivo);
        historicoCardRepository.save(historico);

        return bloqueio;
    }
}
