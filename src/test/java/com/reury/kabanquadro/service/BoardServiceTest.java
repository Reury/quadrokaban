package com.reury.kabanquadro.service;

import com.reury.kabanquadro.model.Board;
import com.reury.kabanquadro.model.Coluna;
import com.reury.kabanquadro.model.TipoColuna;
import com.reury.kabanquadro.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoardServiceUnitTest {

    private BoardRepository boardRepository;
    private BoardService boardService;

    @BeforeEach
    void setUp() {
        boardRepository = mock(BoardRepository.class);
        boardService = new BoardService(boardRepository);
    }

    @Test
    void naoPermiteBoardComNomeDuplicado() {
        String nome = "Duplicado";
        when(boardRepository.existsByNome(nome)).thenReturn(true);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            boardService.criarBoard(nome);
        });

        assertEquals("Já existe um board com esse nome.", ex.getMessage());
    }

    @Test
    void criaBoardComNomeValido() {
        String nome = "Novo Board";
        when(boardRepository.existsByNome(nome)).thenReturn(false);
        when(boardRepository.save(any(Board.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Board board = boardService.criarBoard(nome);

        assertNotNull(board);
        assertEquals(nome, board.getNome());
    }
    
    @Test
    void adicionaColunaComNomeValido() {
        // Arrange
        Board board = new Board();
        board.setColunas(new java.util.HashSet<>());
        String nomeColuna = "Coluna Extra";
        int ordem = 5;
        TipoColuna tipo = TipoColuna.PERSONALIZADA;

        // Simula o save retornando o próprio board
        when(boardRepository.save(any(Board.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Coluna novaColuna = boardService.adicionarColuna(board, nomeColuna, ordem, tipo);

        // Assert
        assertNotNull(novaColuna);
        assertEquals(nomeColuna, novaColuna.getNome());
        assertEquals(ordem, novaColuna.getOrdem());
        assertEquals(tipo, novaColuna.getTipo());
        assertTrue(board.getColunas().contains(novaColuna));
    }
}