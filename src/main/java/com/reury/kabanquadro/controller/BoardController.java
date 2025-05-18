package com.reury.kabanquadro.controller;

import com.reury.kabanquadro.model.*;
import com.reury.kabanquadro.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private CardService cardService;
    @Autowired
    private MovimentacaoService movimentacaoService;
    @Autowired
    private RelatorioService relatorioService;
    @Autowired
    private GestaoService gestaoService;
    @Autowired
    private HistoricoService historicoService;

    // Listar boards ativos
    @GetMapping
    public List<Board> listarBoards() {
        // Implemente um método no repository: findByArquivadoFalse()
        // return boardRepository.findByArquivadoFalse();
        // Ou filtre manualmente:
        return boardService.listarBoardsAtivos();
    }

    // Criar board
    @PostMapping
    public Board criarBoard(@RequestParam String nome) {
        return boardService.criarBoard(nome);
    }

    // Adicionar coluna ao board
    @PostMapping("/{boardId}/colunas")
    public void adicionarColuna(@PathVariable Long boardId, @RequestParam String nome, @RequestParam int ordem, @RequestParam TipoColuna tipo) {
        Board board = boardService.buscarPorId(boardId);
        boardService.adicionarColuna(board, nome, ordem, tipo);
    }

    // Criar card na coluna inicial
    @PostMapping("/{boardId}/cards")
    public Card criarCard(@PathVariable Long boardId, @RequestParam String titulo, @RequestParam String descricao) {
        Board board = boardService.buscarPorId(boardId);
        Coluna colunaInicial = board.getColunas().stream()
                .filter(c -> c.getTipo() == TipoColuna.INICIAL)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Coluna inicial não encontrada"));
        return cardService.criarCard(titulo, descricao, colunaInicial);
    }

    // Mover card para próxima coluna
    @PostMapping("/cards/{cardId}/mover")
    public Card moverCard(@PathVariable Long cardId) {
        Card card = cardService.buscarPorId(cardId);
        return cardService.moverParaProximaColuna(card);
    }

    // Cancelar card
    @PostMapping("/cards/{cardId}/cancelar")
    public Card cancelarCard(@PathVariable Long cardId) {
        Card card = cardService.buscarPorId(cardId);
        return cardService.cancelarCard(card);
    }

    // Bloquear card
    @PostMapping("/cards/{cardId}/bloquear")
    public void bloquearCard(@PathVariable Long cardId, @RequestParam String motivo) {
        Card card = cardService.buscarPorId(cardId);
        // Use BloqueioService se quiser registrar histórico e bloqueio
        // bloqueioService.bloquearCard(card, motivo);
        cardService.bloquearCard(card, motivo);
    }

    // Desbloquear card
    @PostMapping("/cards/{cardId}/desbloquear")
    public void desbloquearCard(@PathVariable Long cardId, @RequestParam String motivo) {
        Card card = cardService.buscarPorId(cardId);
        // Use BloqueioService se quiser registrar histórico e bloqueio
        // bloqueioService.desbloquearCard(card, motivo);
        cardService.desbloquearCard(card, motivo);
    }

    // Relatório de tempo de tarefas por card
    @GetMapping("/cards/{cardId}/tempo-total")
    public long tempoTotalCard(@PathVariable Long cardId) {
        Card card = cardService.buscarPorId(cardId);
        return relatorioService.tempoTotalCard(card);
    }

    // Relatório geral do board
    @GetMapping("/{boardId}/resumo")
    public Map<TipoColuna, Long> resumoBoard(@PathVariable Long boardId) {
        Board board = boardService.buscarPorId(boardId);
        return relatorioService.resumoCardsPorTipoColuna(board);
    }

    // Histórico detalhado do card
    @GetMapping("/cards/{cardId}/historico")
    public List<HistoricoCard> historicoCard(@PathVariable Long cardId) {
        Card card = cardService.buscarPorId(cardId);
        return historicoService.historicoPorCard(card);
    }

    // Excluir board
    @DeleteMapping("/{boardId}")
    public void excluirBoard(@PathVariable Long boardId) {
        Board board = boardService.buscarPorId(boardId);
        gestaoService.excluirBoard(board);
    }

    // Arquivar board
    @PostMapping("/{boardId}/arquivar")
    public void arquivarBoard(@PathVariable Long boardId) {
        Board board = boardService.buscarPorId(boardId);
        gestaoService.arquivarBoard(board);
    }
}
