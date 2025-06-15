package com.reury.kabanquadro.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.reury.kabanquadro.model.TipoColuna;

@SpringBootTest
@ActiveProfiles("ci") // ADICIONE ESTA LINHA
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    void deveCriarBoardComNomeValido(){
        String nomeUnico = "Teste Board " + System.currentTimeMillis();
        var board = boardService.criarBoard(nomeUnico);
        assertNotNull(board);
        assertEquals(nomeUnico, board.getNome());
    }

    @Test
    void deveLancarExcecaoAoCriarBoardComNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            boardService.criarBoard("");
        });
    }
    @Test
    void deveLancarExcecaoAoCriarBoardComNomeNulo(){
        assertThrows(IllegalArgumentException.class, () -> {
            boardService.criarBoard(null);
        });
    }
    @Test
    void deveLancarExcecaoAoCriarBoardComNomeDuplicado() {
        String nomeUnico = "Board Duplicado " + System.currentTimeMillis();
        boardService.criarBoard(nomeUnico);
        assertThrows(IllegalArgumentException.class, () -> {
            boardService.criarBoard(nomeUnico);
        });
    }
    @Test
    void deveCriarBoardComColunasObrigatorias() {
        String nomeUnico = "Board com Colunas Obrigatórias " + System.currentTimeMillis();
        var board = boardService.criarBoard(nomeUnico);
        
        assertNotNull(board.getColunas());
        assertEquals(4, board.getColunas().size());
        
        // Verificar colunas obrigatórias
        assertTrue(board.getColunas().stream().anyMatch(c -> c.getNome().equals("Inicial")));
        assertTrue(board.getColunas().stream().anyMatch(c -> c.getNome().equals("Pendente")));
        assertTrue(board.getColunas().stream().anyMatch(c -> c.getNome().equals("Final")));
        assertTrue(board.getColunas().stream().anyMatch(c -> c.getNome().equals("Cancelamento")));
    }

    @Test 
    void deveAdicionarColunaValida(){
        String nomeUnico = "Board para Adicionar Coluna " + System.currentTimeMillis();
        var board = boardService.criarBoard(nomeUnico);
        
        String nomeColuna = "Nova Coluna";
        int ordem = 5;
        var coluna = boardService.adicionarColuna(board, nomeColuna, ordem, TipoColuna.PENDENTE);
        
        assertNotNull(coluna);
        assertEquals(nomeColuna, coluna.getNome());
        assertEquals(ordem, coluna.getOrdem());
    }

}