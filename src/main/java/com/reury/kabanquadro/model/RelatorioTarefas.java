package com.reury.kabanquadro.model;


import java.time.LocalDateTime;

public class RelatorioTarefas {
    private int id;
    private int boardId;
    private int cardId;
    private LocalDateTime dataCriacao;
    private int tempoTotalNasTarefas;
    private boolean concluido;
    private String tempoDetalhesJson;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getBoardId() {
        return boardId;
    }
    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }
    public int getCardId() {
        return cardId;
    }
    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public int getTempoTotalNasTarefas() {
        return tempoTotalNasTarefas;
    }
    public void setTempoTotalNasTarefas(int tempoTotalNasTarefas) {
        this.tempoTotalNasTarefas = tempoTotalNasTarefas;
    }
    public boolean isConcluido() {
        return concluido;
    }
    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }
    public String getTempoDetalhesJson() {
        return tempoDetalhesJson;
    }
    public void setTempoDetalhesJson(String tempoDetalhesJson) {
        this.tempoDetalhesJson = tempoDetalhesJson;
    }

    // 
}