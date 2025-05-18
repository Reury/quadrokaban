package com.reury.kabanquadro.repository;

import com.reury.kabanquadro.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    boolean existsByNome(String nome);
    List<Board> findByArquivadoFalse(); // Boards não arquivados (ativos)
    List<Board> findByArquivadoTrue();  // Boards arquivados
}