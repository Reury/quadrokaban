package com.reury.kabanquadro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.reury.kabanquadro.model.Board;
import com.reury.kabanquadro.model.Card;
import com.reury.kabanquadro.model.Coluna;
import com.reury.kabanquadro.model.TipoColuna;

public class GestaoServiceTest {
    private GestaoService gestaoService;
    
    @BeforeEach
    void setUp() {
        // Initialize the GestaoService with necessary dependencies
        gestaoService = new GestaoService();
    }
    @Test
    void naoPermitirExcluirBoardComCardsAtivos() {
        // Arrange
        Board board = new Board();
        board.setId(1L);
        board.setNome("Board com Cards Ativos");
        board.setColunas(new HashSet<>()); // Garante que a coleção está inicializada

        Coluna colunaInicial = new Coluna();
        colunaInicial.setTipo(TipoColuna.INICIAL);
        colunaInicial.setCards(new HashSet<>()); // Garante que a coleção está inicializada

        board.getColunas().add(colunaInicial);

        Card card1 = new Card();
        card1.setId(1L);
        card1.setTitulo("Card Ativo 1");
        colunaInicial.getCards().add(card1);

        // Act & Assert
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            gestaoService.excluirBoard(board);
        });

        assertEquals("Não é possível excluir um board com cards ativos.", ex.getMessage());
    }

    @Test
    void naoPermitirExcluirColunaObrigatoria() {
        // Arrange
        Coluna coluna = new Coluna();
        coluna.setTipo(TipoColuna.INICIAL);
        
        // Act & Assert
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            gestaoService.excluirColuna(coluna);
        });

        assertEquals("Não é permitido excluir colunas obrigatórias.", ex.getMessage());
    }
}
