package com.reury.kabanquadro.repository;

import com.reury.kabanquadro.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByArquivadoFalse();
    List<Card> findByArquivadoTrue();
}