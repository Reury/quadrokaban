package com.reury.kabanquadro.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
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

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board; }
    public Card getCard() { return card; }
    public void setCard(Card card) { this.card = card; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public int getTempoTotalNasTarefas() { return tempoTotalNasTarefas; }
    public void setTempoTotalNasTarefas(int tempoTotalNasTarefas) { this.tempoTotalNasTarefas = tempoTotalNasTarefas; }
    public boolean isConcluido() { return concluido; }
    public void setConcluido(boolean concluido) { this.concluido = concluido; }
    public String getTempoDetalhesJson() { return tempoDetalhesJson; }
    public void setTempoDetalhesJson(String tempoDetalhesJson) { this.tempoDetalhesJson = tempoDetalhesJson; }
}