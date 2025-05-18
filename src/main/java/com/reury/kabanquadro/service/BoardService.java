package com.reury.kabanquadro.service;

import com.reury.kabanquadro.model.Board;
import com.reury.kabanquadro.model.Coluna;
import com.reury.kabanquadro.model.TipoColuna;
import com.reury.kabanquadro.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        List<Coluna> colunas = new ArrayList<>();
        colunas.add(new Coluna("Inicial", 1, TipoColuna.INICIAL, board));
        colunas.add(new Coluna("Pendente", 2, TipoColuna.PENDENTE, board));
        colunas.add(new Coluna("Final", 3, TipoColuna.FINAL, board));
        colunas.add(new Coluna("Cancelamento", 4, TipoColuna.CANCELAMENTO, board));
        board.setColunas(colunas);

        // Salvar board (cascata salva colunas)
        return boardRepository.save(board);
    }

    public void adicionarColuna(Board board, String nomeNovaColuna, int ordem, TipoColuna tipoColuna) {
        // Validar coluna
        if (nomeNovaColuna == null || nomeNovaColuna.isBlank()) {
            throw new IllegalArgumentException("Nome da coluna é obrigatório.");
        }
        boolean nomeDuplicado = board.getColunas().stream()
            .anyMatch(coluna -> coluna.getNome().equalsIgnoreCase(nomeNovaColuna));
        if (nomeDuplicado) {
            throw new IllegalArgumentException("Já existe uma coluna com esse nome neste board.");
        }

        // Adicionar coluna
        Coluna novaColuna = new Coluna(nomeNovaColuna, ordem, tipoColuna, board);
        board.getColunas().add(novaColuna);
        boardRepository.save(board);
    }

    public Board buscarPorId(Long id) {
        return boardRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Board não encontrado!"));
    }

    public List<Board> listarBoardsAtivos() {
        return boardRepository.findByArquivadoFalse();
    }
}
