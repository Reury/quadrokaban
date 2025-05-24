package com.reury.kabanquadro.service;

import com.reury.kabanquadro.model.Board;
import com.reury.kabanquadro.model.Coluna;
import com.reury.kabanquadro.model.TipoColuna;
import com.reury.kabanquadro.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ColunaService colunaService;

    public Board criarBoard(String nome) {
        // Validar nome
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do board é obrigatório.");
        }
        if (boardRepository.existsByNome(nome)) {
            throw new IllegalArgumentException("Já existe um board com esse nome.");
        }

        // Criar board
        Board board = new Board();
        board.setNome(nome);
        board.setDataCriacao(LocalDateTime.now());
        board.setAtivo(true);

        // Criar colunas obrigatórias
        Set<Coluna> colunas = new HashSet<>();
        colunas.add(new Coluna("Inicial", 1, TipoColuna.INICIAL, board));
        colunas.add(new Coluna("Pendente", 2, TipoColuna.PENDENTE, board));
        colunas.add(new Coluna("Final", 3, TipoColuna.FINAL, board));
        colunas.add(new Coluna("Cancelamento", 4, TipoColuna.CANCELAMENTO, board));
        board.setColunas(colunas);

        // Salvar board (cascata salva colunas)
        return boardRepository.save(board);
    }

    public Coluna adicionarColuna(Board board, String nomeNovaColuna, int ordem, TipoColuna tipoColuna) {
        // Validar nome da coluna
        if (nomeNovaColuna == null || nomeNovaColuna.isBlank()) {
            throw new IllegalArgumentException("Nome da coluna é obrigatório.");
        }
        boolean nomeDuplicado = board.getColunas().stream()
            .anyMatch(coluna -> coluna.getNome().equalsIgnoreCase(nomeNovaColuna));
        if (nomeDuplicado) {
            throw new IllegalArgumentException("Já existe uma coluna com esse nome neste board.");
        }

        // Impedir tipos duplicados
        boolean tipoDuplicado = board.getColunas().stream()
            .anyMatch(coluna -> coluna.getTipo() == tipoColuna);
        if (tipoDuplicado && (tipoColuna == TipoColuna.INICIAL || tipoColuna == TipoColuna.FINAL || tipoColuna == TipoColuna.CANCELAMENTO)) {
            throw new IllegalArgumentException("Já existe uma coluna obrigatória deste tipo no board.");
        }

        // Validar ordem da coluna
        boolean ordemDuplicada = board.getColunas().stream()
            .anyMatch(coluna -> coluna.getOrdem() == ordem);
        if (ordemDuplicada) {
            throw new IllegalArgumentException("Já existe uma coluna com essa ordem neste board.");
        }

        // Adicionar coluna
        Coluna novaColuna = new Coluna(nomeNovaColuna, ordem, tipoColuna, board);
        board.getColunas().add(novaColuna);
        boardRepository.save(board);
        return novaColuna;
    }

    public Board buscarPorId(Long id) {
        return boardRepository.findByIdAndArquivadoFalse(id)
            .orElseThrow(() -> new IllegalArgumentException("Board não encontrado!"));
    }

    public List<Board> listarBoardsAtivos() {
        return boardRepository.findByArquivadoFalse();
    }

    public Board buscarBoardComColunas(Long id) {
        Board board = boardRepository.findByIdAndArquivadoFalse(id)
            .orElseThrow(() -> new IllegalArgumentException("Board não encontrado!"));
        // Força a inicialização dos cards de cada coluna
        board.getColunas().forEach(coluna -> coluna.getCards().size());
        return board;
    }

    public void excluirBoard(Board board) {
        // Verifica se o board está arquivado
        if (board.isAtivo()) {
            throw new IllegalStateException("Não é permitido excluir boards ativos.");
        }

        // Verifica se o board possui cards ativos
        boolean temCardsAtivos = board.getColunas().stream()
            .anyMatch(coluna -> !coluna.getCards().isEmpty());
        if (temCardsAtivos) {
            throw new IllegalStateException("Não é permitido excluir boards com cards ativos.");
        }

        // Excluir board
        boardRepository.delete(board);
    }

    public void arquivarBoard(Board board) {
        // Verifica se o board possui cards ativos
        boolean temCardsAtivos = board.getColunas().stream()
            .anyMatch(coluna -> !coluna.getCards().isEmpty());
        if (temCardsAtivos) {
            throw new IllegalStateException("Não é permitido arquivar boards com cards ativos.");
        }

        // Arquivar board
        board.setAtivo(false);
        boardRepository.save(board);
    }
}
