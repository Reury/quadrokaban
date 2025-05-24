package com.reury.kabanquadro.service;

import com.reury.kabanquadro.dao.BloqueioDao;
import com.reury.kabanquadro.dto.BloqueioDto;
import com.reury.kabanquadro.model.*;
import com.reury.kabanquadro.repository.CardRepository;
import com.reury.kabanquadro.repository.HistoricoCardRepository;
import com.reury.kabanquadro.repository.BloqueioRepository;
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

    public List<BloqueioDto> listarBloqueiosPorBoard(Long boardId) {
        return bloqueioDao.listarBloqueiosPorBoard(boardId);
    }

    // Bloqueia um card
    public Bloqueio bloquearCard(Card card, String motivo) {
        if (card.isBloqueado()) {
            throw new IllegalStateException("Card já está bloqueado.");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Motivo do bloqueio é obrigatório.");
        }

        card.setBloqueado(true);
        cardRepository.save(card);

        Bloqueio bloqueio = new Bloqueio();
        bloqueio.setCard(card);
        bloqueio.setDataHoraBloqueio(LocalDateTime.now());
        bloqueio.setMotivoBloqueio(motivo);
        bloqueioRepository.save(bloqueio); // Use o repository aqui

        // Registrar histórico
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
        if (!card.isBloqueado()) {
            throw new IllegalStateException("Card não está bloqueado.");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Motivo do desbloqueio é obrigatório.");
        }

        // Busca o último bloqueio aberto para esse card
        Bloqueio bloqueio = bloqueioRepository.findTopByCardOrderByDataHoraBloqueioDesc(card)
                .orElseThrow(() -> new IllegalStateException("Nenhum bloqueio encontrado para este card."));

        bloqueio.setDataHoraDesbloqueio(LocalDateTime.now());
        bloqueio.setMotivoDesbloqueio(motivo);
        bloqueio.setTempoBloqueado(
                (int) java.time.Duration.between(
                        bloqueio.getDataHoraBloqueio(), bloqueio.getDataHoraDesbloqueio()
                ).toSeconds()
        );
        bloqueioRepository.save(bloqueio); // Use o repository aqui

        card.setBloqueado(false);
        cardRepository.save(card);

        // Registrar histórico
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
