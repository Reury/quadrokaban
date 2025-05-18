package com.reury.kabanquadro.repository;

import com.reury.kabanquadro.model.Movimentacao;
import com.reury.kabanquadro.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByCard(Card card);
}