package com.reury.kabanquadro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.reury.kabanquadro.model.Board;
import com.reury.kabanquadro.model.Card;
import com.reury.kabanquadro.model.Coluna;
import com.reury.kabanquadro.model.TipoColuna;
import com.reury.kabanquadro.repository.CardRepository;

public class CardServiceTest {
    
    private CardRepository cardRepository;
    private CardService cardService;

    @BeforeEach
    void setUp() {
        cardRepository = mock(CardRepository.class);
        cardService = new CardService(cardRepository);
    }

    @Test
    void naoPermitirCriarCardComTituloVazio() {
        Card card = new Card();
        card.setTitulo("");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            cardService.criarCard("", "Descrição do card", null);
        });

        assertEquals("Título do card é obrigatório.", ex.getMessage());
    }

    @Test
    void naoPermitirCriarCardComDescricaoVazia() {
        Card card = new Card();
        card.setDescricao("");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            cardService.criarCard("Título do card", "", null);
        });

        assertEquals("Descrição do card é obrigatória.", ex.getMessage());
    }

    @Test
    void naoPermitirCriarCardNaColunaIncorreta() {
        Card card = new Card();
        card.setColuna(null); // Simula coluna inválida
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            cardService.criarCard("Título do card", "Descrição do card", null);
        });

        assertEquals("Card deve ser criado na coluna INICIAL.", ex.getMessage());
    }

    @Test
    void naoPermitirCriarCardComTituloIgualAOOutro() {
        // Arrange
        Board board = new Board();
        board.setColunas(new HashSet<>());

        Coluna coluna = new Coluna();
        coluna.setTipo(TipoColuna.INICIAL);
        coluna.setCards(new HashSet<>());
        coluna.setBoard(board);

        board.getColunas().add(coluna);

        Card cardExistente = new Card();
        cardExistente.setTitulo("Card Duplicado");
        coluna.getCards().add(cardExistente);

        // Act & Assert
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            cardService.criarCard("Card Duplicado", "Outra descrição", coluna);
        });

        assertEquals("Já existe um card com esse título no board.", ex.getMessage());
    }
    @Test
    void naoPermitirMoverCardParaProximaColunaSeBloqueado() {
        Card card = new Card();
        card.setBloqueado(true); // Simula card bloqueado
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            cardService.moverParaProximaColuna(card);
        });

        assertEquals("Card está bloqueado e não pode ser movido.", ex.getMessage());
    }
    @Test
    void naoPermitirMoverParaColunaFinal() {
        Card card = new Card();
        Coluna colunaFinal = new Coluna();
        colunaFinal.setTipo(TipoColuna.FINAL);
        card.setColuna(colunaFinal); // Simula card na coluna final
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            cardService.moverParaProximaColuna(card);
        });

        assertEquals("Card já está na coluna FINAL e não pode ser movido.", ex.getMessage());
    }

    @Test
    void naoPermitirCancelarCardJaCancelado() {        
        Coluna colunaFinal = new Coluna();
        colunaFinal.setTipo(TipoColuna.CANCELAMENTO);
        Card card = new Card();
        card.setColuna(colunaFinal); 
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            cardService.cancelarCard(card);
        });

        assertEquals("Card já está na coluna de cancelamento.", ex.getMessage());
    }

    @Test
    void naoPermitirCancelarCardEmColunaFinal() {
        Coluna colunaFinal = new Coluna();
        colunaFinal.setTipo(TipoColuna.FINAL);
        Card card = new Card();
        card.setColuna(colunaFinal); // Simula card na coluna final
        
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            cardService.cancelarCard(card);
        });

        assertEquals("Não é permitido cancelar um card que já está na coluna FINAL.", ex.getMessage());
    }
    @Test
    void naoPermitirCancelarCardBloqueado() {
        Card card = new Card();
        card.setBloqueado(true); // Simula card bloqueado
        
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            cardService.cancelarCard(card);
        });

        assertEquals("Card está bloqueado e não pode ser cancelado.", ex.getMessage());
    }

    @Test
    void naoPermitirBloquearCardSemMotivo(){
        Card card = new Card();
        card.setBloqueado(false); // Card não está bloqueado
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            cardService.bloquearCard(card, null);
        });
        assertEquals("Motivo do bloqueio é obrigatório.", ex.getMessage());
        
    }
    @Test
    void naoPermitirBloquearCardEmColunaFinal() {
        Card card = new Card();
        card.setBloqueado(false); // Card não está bloqueado
        Coluna colunaFinal = new Coluna();
        colunaFinal.setTipo(TipoColuna.FINAL); // Coluna FINAL
        card.setColuna(colunaFinal);
        
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            cardService.bloquearCard(card, "Motivo de teste");
        });

        assertEquals("Não é permitido bloquear um card em uma coluna FINAL ou CANCELAMENTO.", ex.getMessage());
    }

    @Test
    void naoPermitirBloquearCardEmColunaCancelamento() {
        Card card = new Card();
        card.setBloqueado(false); // Card não está bloqueado
        Coluna colunaCancelamento = new Coluna();
        colunaCancelamento.setTipo(TipoColuna.CANCELAMENTO); // Coluna CANCELAMENTO
        card.setColuna(colunaCancelamento);
        Exception ex = assertThrows(IllegalStateException.class, () -> {    
            cardService.bloquearCard(card, "Motivo de teste");
        });     
        assertEquals("Não é permitido bloquear um card em uma coluna FINAL ou CANCELAMENTO.", ex.getMessage());
    }

    @Test
    void naoPermitirDesbloquearCardJaDesbloqueado() {
        Card card = new Card();
        card.setBloqueado(false); // Card já está desbloqueado
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            cardService.desbloquearCard(card, "Motivo de desbloqueio");
        });

        assertEquals("Card não está bloqueado.", ex.getMessage());
    }
    @Test
    void naoPermitirDesbloquearCardSemMotivo() {
        Card card = new Card();
        card.setBloqueado(true); // Card está bloqueado
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            cardService.desbloquearCard(card, null);
        });

        assertEquals("Motivo do desbloqueio é obrigatório.", ex.getMessage());
    }

    @Test
    void naoPermitirExcluirCardBloqueado() {
        Card card = new Card();
        card.setBloqueado(true); // Card está bloqueado
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            cardService.excluirCard(card);
        });

        assertEquals("Não é permitido excluir um card bloqueado.", ex.getMessage());
    }

    @Test
    void naoPermitirExcluirCardEmColunaFinal() {
        Card card = new Card();
        Coluna colunaFinal = new Coluna();
        colunaFinal.setTipo(TipoColuna.FINAL); // Coluna FINAL
        card.setColuna(colunaFinal);
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            cardService.excluirCard(card);
        });

        assertEquals("Não é permitido excluir um card que está na coluna FINAL.", ex.getMessage());
    }
}
