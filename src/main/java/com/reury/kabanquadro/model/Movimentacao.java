package com.reury.kabanquadro.model;


import java.time.LocalDateTime;

public class Movimentacao {
    private int id;
    private int cardId;
    private int colunaOrigemId;
    private int colunaDestinoId;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private int tempoNaColuna;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCardId() {
        return cardId;
    }
    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
    public int getColunaOrigemId() {
        return colunaOrigemId;
    }
    public void setColunaOrigemId(int colunaOrigemId) {
        this.colunaOrigemId = colunaOrigemId;
    }
    public int getColunaDestinoId() {
        return colunaDestinoId;
    }
    public void setColunaDestinoId(int colunaDestinoId) {
        this.colunaDestinoId = colunaDestinoId;
    }
    public LocalDateTime getDataHoraEntrada() {
        return dataHoraEntrada;
    }
    public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) {
        this.dataHoraEntrada = dataHoraEntrada;
    }
    public LocalDateTime getDataHoraSaida() {
        return dataHoraSaida;
    }
    public void setDataHoraSaida(LocalDateTime dataHoraSaida) {
        this.dataHoraSaida = dataHoraSaida;
    }
    public int getTempoNaColuna() {
        return tempoNaColuna;
    }
    public void setTempoNaColuna(int tempoNaColuna) {
        this.tempoNaColuna = tempoNaColuna;
    }

    // getters e setters
}