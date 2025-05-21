package com.reury.kabanquadro.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RelatorioTarefas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    private LocalDateTime dataCriacao;
    private int tempoTotalNasTarefas;
    private boolean concluido;
    private String tempoDetalhesJson;


}