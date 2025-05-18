package com.reury.kabanquadro.repository;

import com.reury.kabanquadro.model.Coluna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColunaRepository extends JpaRepository<Coluna, Long> {
    List<Coluna> findByArquivadoFalse();
    List<Coluna> findByArquivadoTrue();
}