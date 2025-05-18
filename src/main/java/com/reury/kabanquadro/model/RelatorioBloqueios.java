package com.reury.kabanquadro.model;


public class RelatorioBloqueios {
    private int id;
    private int boardId;
    private int cardId;
    private int quantidadeBloqueios;
    private int tempoTotalBloqueado;
    private String motivosBloqueioJson;
    private String motivosDesbloqueioJson;
    
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
    public int getQuantidadeBloqueios() {
        return quantidadeBloqueios;
    }
    public void setQuantidadeBloqueios(int quantidadeBloqueios) {
        this.quantidadeBloqueios = quantidadeBloqueios;
    }
    public int getTempoTotalBloqueado() {
        return tempoTotalBloqueado;
    }
    public void setTempoTotalBloqueado(int tempoTotalBloqueado) {
        this.tempoTotalBloqueado = tempoTotalBloqueado;
    }
    public String getMotivosBloqueioJson() {
        return motivosBloqueioJson;
    }
    public void setMotivosBloqueioJson(String motivosBloqueioJson) {
        this.motivosBloqueioJson = motivosBloqueioJson;
    }
    public String getMotivosDesbloqueioJson() {
        return motivosDesbloqueioJson;
    }
    public void setMotivosDesbloqueioJson(String motivosDesbloqueioJson) {
        this.motivosDesbloqueioJson = motivosDesbloqueioJson;
    }

    // getters e setters
}