package com.reury.kabanquadro.service;

import com.reury.kabanquadro.model.Board;
import com.reury.kabanquadro.model.Card;
import com.reury.kabanquadro.model.Coluna;
import com.reury.kabanquadro.model.TipoColuna;
import com.reury.kabanquadro.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

class BoardServiceUnitTest {

    private BoardRepository boardRepository;
    private BoardService boardService;

    @BeforeEach
    void setUp() {
        boardRepository = mock(BoardRepository.class);
        boardService = new BoardService(boardRepository);
    }

    @Test
    void criaBoardComNomeValido() {
        // Testa criação de board com nome válido
        String nome = "Novo Board";
        when(boardRepository.existsByNome(nome)).thenReturn(false);
        when(boardRepository.save(any(Board.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Board board = boardService.criarBoard(nome);

        assertNotNull(board);
        assertEquals(nome, board.getNome());
    }

    @Test
    void naoPermiteBoardComNomeDuplicado() {
        // Testa erro ao criar board com nome duplicado
        String nome = "Duplicado";
        when(boardRepository.existsByNome(nome)).thenReturn(true);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            boardService.criarBoard(nome);
        });

        assertEquals("Já existe um board com esse nome.", ex.getMessage());
    }

    @Test
    void adicionaColunaComSucesso() {
        // Testa adicionar coluna válida em board ativo
        Board board = new Board();
        board.setColunas(new HashSet<>());
        board.setAtivo(true);
        String nomeColuna = "Coluna Nova";
        int ordem = 5;
        TipoColuna tipo = TipoColuna.PERSONALIZADA;

        when(boardRepository.save(any(Board.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Coluna novaColuna = boardService.adicionarColuna(board, nomeColuna, ordem, tipo);

        assertNotNull(novaColuna);
        assertEquals(nomeColuna, novaColuna.getNome());
        assertEquals(ordem, novaColuna.getOrdem());
        assertEquals(tipo, novaColuna.getTipo());
        assertTrue(board.getColunas().contains(novaColuna));
    }

    @Test
    void naoPermiteAdicionarColunaComNomeDuplicado() {
        // Testa erro ao adicionar coluna com nome duplicado
        Board board = new Board();
        Coluna colunaExistente = new Coluna();
        colunaExistente.setNome("Coluna Extra");
        board.setColunas(new HashSet<>(List.of(colunaExistente)));

        when(boardRepository.save(any(Board.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            boardService.adicionarColuna(board, "Coluna Extra", 5, TipoColuna.PERSONALIZADA);
        });

        assertEquals("Já existe uma coluna com esse nome neste board.", ex.getMessage());
    }

    @Test
    void naoPermiteAdicionarColunaComOrdemDuplicada() {
        // Testa erro ao adicionar coluna com ordem duplicada
        Board board = new Board();
        Coluna colunaExistente = new Coluna();
        colunaExistente.setNome("teste");
        colunaExistente.setOrdem(1);
        board.setColunas(new HashSet<>(List.of(colunaExistente)));

        when(boardRepository.save(any(Board.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            boardService.adicionarColuna(board, "Coluna Duplicada", 1, TipoColuna.PERSONALIZADA);
        });

        assertEquals("Já existe uma coluna com essa ordem neste board.", ex.getMessage());
    }

    @Test
    void naoPermiteAdicionarColunaObrigatoriaDuplicada() {
        // Testa erro ao adicionar coluna obrigatória com nome duplicado
        Board board = new Board();
        Coluna colunaExistente = new Coluna();
        colunaExistente.setNome("Coluna Obrigatória");
        board.setColunas(new HashSet<>(List.of(colunaExistente)));

        when(boardRepository.save(any(Board.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            boardService.adicionarColuna(board, "Coluna Obrigatória", 2, TipoColuna.PERSONALIZADA);
        });

        assertEquals("Já existe uma coluna com esse nome neste board.", ex.getMessage());
    }

    @Test
    void naoPermiteAdicionarColunaEmBoardArquivado() {
        // Testa erro ao adicionar coluna em board arquivado
        Board board = new Board();
        board.setAtivo(false);
        board.setColunas(new HashSet<>());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            boardService.adicionarColuna(board, "Coluna 1", 2, TipoColuna.PERSONALIZADA);
        });

        assertEquals("não é permitido adicionar colunas em board arquivado.", ex.getMessage());
    }

    @Test
    void naoPermiteExcluirBoardComCardsAtivos() {
        // Testa erro ao excluir board com cards ativos
        Board board = new Board();
        board.setAtivo(true);

        Coluna coluna = new Coluna();
        coluna.setCards(new HashSet<>());

        Card card = new Card();
        card.setArquivado(false);

        coluna.getCards().add(card);
        board.setColunas(new HashSet<>());
        board.getColunas().add(coluna);

        Exception ex = assertThrows(IllegalStateException.class, () -> {
            boardService.excluirBoard(board);
        });

        assertEquals("Não é permitido excluir boards ativos.", ex.getMessage());
    }

    @Test
    void naoPermiteCriarBoardComNomeVazio() {
        // Testa erro ao criar board com nome vazio
        String nome = "";
        when(boardRepository.existsByNome(nome)).thenReturn(false);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            boardService.criarBoard(nome);
        });

        assertEquals("Nome do board é obrigatório.", ex.getMessage());
    }

    @Test
    void buscaBoardPorIdComSucesso() {
        // Testa busca de board por ID
        Board board = new Board();
        board.setId(1L);
        when(boardRepository.findByIdAndArquivadoFalse(1L)).thenReturn(Optional.of(board));

        Board boardEncontrado = boardService.buscarPorId(1L);

        assertNotNull(boardEncontrado);
        assertEquals(board.getId(), boardEncontrado.getId());
    }

    @Test
    void buscaBoardPorIdNaoExistenteLancaExcecao() {
        // Testa erro ao buscar board com ID não existente
        when(boardRepository.findByIdAndArquivadoFalse(99L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            boardService.buscarPorId(99L);
        });

        assertEquals("Board não encontrado!", ex.getMessage());
    }
}