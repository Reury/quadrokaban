package com.reury.kabanquadro.service;




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.reury.kabanquadro.model.Bloqueio;
import com.reury.kabanquadro.model.Board;
import com.reury.kabanquadro.model.Card;
import com.reury.kabanquadro.model.Coluna;
import com.reury.kabanquadro.model.TipoColuna;
import com.reury.kabanquadro.repository.BloqueioRepository;
import com.reury.kabanquadro.repository.BoardRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class BloqueioServiceTest {

    private BloqueioRepository bloqueioRepository;
    private BloqueioService bloqueioService;
    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        bloqueioRepository = mock(BloqueioRepository.class);
        boardRepository = mock(BoardRepository.class);
        bloqueioService = new BloqueioService(bloqueioRepository, boardRepository);
    }

    @Test
    void naoPermitirBloquearCardJaBloqueado(){
        Card card = new Card();
        card.setBloqueado(true);        

        Exception ex = assertThrows(IllegalStateException.class,() ->{
            bloqueioService.bloquearCard(card, "Motivo de teste");
        });

        assertEquals("Card já está bloqueado.", ex.getMessage());
    }

    @Test
    void naoPermitirDesbloquearCardJaDesbloqueado() {
        Card card = new Card();
        card.setBloqueado(false); // Card já está desbloqueado

        Exception ex = assertThrows(IllegalStateException.class, () -> {
            bloqueioService.desbloquearCard(card, "teste de desbloqueio,quando card já está desbloqueado");
        });

        assertEquals("Card não está bloqueado.", ex.getMessage());
    }

    @Test
    void naoPermitirBloquearCardSemMotivo(){
        Card card = new Card();
        card.setBloqueado(false);

        Exception ex = assertThrows(IllegalArgumentException.class,()->{
            bloqueioService.desbloquearCard(card, null);
        });

        assertEquals("Motivo do desbloqueio é obrigatório.", ex.getMessage());
    }

    @Test
    void naoPermitirGerarRelatorioParaBoardArquivado() {
        Board board = new Board();
        board.setId(1L);
        board.setArquivado(true); // Simula um board arquivado

        when(boardRepository.findById(1L)).thenReturn(java.util.Optional.of(board));
 
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            bloqueioService.listarBloqueiosPorBoard(1L);
        });

        assertEquals("Não é possível gerar relatórios para boards arquivados.", ex.getMessage());
    }

    @Test
    void naoPermitirGerarRelatorioParaBoardNaoExistente() {
        when(boardRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            bloqueioService.listarBloqueiosPorBoard(1L);
        });

        assertEquals("Board não encontrado.", ex.getMessage());
    }

    @Test
    void naoPermitirBloquearCardEmColunaNaoPermitida() {
        Card card = new Card();
        card.setBloqueado(false);
        Coluna coluna = new Coluna();
        coluna.setTipo(TipoColuna.FINAL); // Coluna TO_DO não permite bloqueio
        card.setColuna(coluna);
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            bloqueioService.bloquearCard(card, "Motivo de teste");
        });

        assertEquals("Não é permitido bloquear cards em colunas FINAL ou CANCELAMENTO.", ex.getMessage());
    }
    
}
