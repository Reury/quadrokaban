package com.reury.kabanquadro.repository;

import com.reury.kabanquadro.model.Board;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    boolean existsByNome(String nome);

    List<Board> findByArquivadoFalse();

    @EntityGraph(attributePaths = {"colunas", "colunas.cards"})
    Optional<Board> findByIdAndArquivadoFalse(Long id);
}