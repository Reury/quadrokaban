package com.reury.kabanquadro.controller;

import com.reury.kabanquadro.dto.*;
import com.reury.kabanquadro.mapper.*;
import com.reury.kabanquadro.model.*;
import com.reury.kabanquadro.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private ColunaMapper colunaMapper;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private HistoricoCardMapper historicoCardMapper;

    // Listar boards ativos
    @GetMapping
    public List<BoardDto> listarBoards() {
        return boardService.listarBoardsAtivos()
                .stream()
                .map(boardMapper::toDto)
                .collect(Collectors.toList());
    }

    // Criar board
    @PostMapping
    public BoardDto criarBoard(@RequestParam String nome) {
        Board board = boardService.criarBoard(nome);
        return boardMapper.toDto(board);
    }

    // Adicionar coluna ao board
    @PostMapping("/{boardId}/colunas")
    public ColunaDto adicionarColuna(@PathVariable Long boardId, @RequestParam String nome, @RequestParam int ordem, @RequestParam TipoColuna tipo) {
        Board board = boardService.buscarPorId(boardId);
        Coluna coluna = boardService.adicionarColuna(board, nome, ordem, tipo);
        return colunaMapper.toDto(coluna);
    }

    // Criar card na coluna inicial
    @PostMapping("/{boardId}/cards")
    public CardDto criarCard(@PathVariable Long boardId, @RequestParam String titulo, @RequestParam String descricao) {
        Board board = boardService.buscarPorId(boardId);
        Coluna colunaInicial = board.getColunas().stream()
                .filter(c -> c.getTipo() == TipoColuna.INICIAL)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Coluna inicial não encontrada"));
        Card card = cardService.criarCard(titulo, descricao, colunaInicial);
        return cardMapper.toDto(card);
    }

    // Mover card para próxima coluna
    @PostMapping("/cards/{cardId}/mover")
    public CardDto moverCard(@PathVariable Long cardId) {
        Card card = cardService.buscarPorId(cardId);
        Card cardMovido = cardService.moverParaProximaColuna(card);
        return cardMapper.toDto(cardMovido);
    }

    // Cancelar card
    @PostMapping("/cards/{cardId}/cancelar")
    public CardDto cancelarCard(@PathVariable Long cardId) {
        Card card = cardService.buscarPorId(cardId);
        Card cardCancelado = cardService.cancelarCard(card);
        return cardMapper.toDto(cardCancelado);
    }

    // Bloquear card
    @PostMapping("/cards/{cardId}/bloquear")
    public void bloquearCard(@PathVariable Long cardId, @RequestParam String motivo) {
        Card card = cardService.buscarPorId(cardId);
        cardService.bloquearCard(card, motivo);
    }

    // Desbloquear card
    @PostMapping("/cards/{cardId}/desbloquear")
    public void desbloquearCard(@PathVariable Long cardId, @RequestParam String motivo) {
        Card card = cardService.buscarPorId(cardId);
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
    public List<HistoricoCardDto> historicoCard(@PathVariable Long cardId) {
        Card card = cardService.buscarPorId(cardId);
        return historicoService.historicoPorCard(card)
                .stream()
                .map(historicoCardMapper::toDto)
                .collect(Collectors.toList());
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
