package com.reury.kabanquadro.repository;

import com.reury.kabanquadro.model.Bloqueio;
import com.reury.kabanquadro.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BloqueioRepository extends JpaRepository<Bloqueio, Long> {
    Optional<Bloqueio> findTopByCardOrderByDataHoraBloqueioDesc(Card card);
}