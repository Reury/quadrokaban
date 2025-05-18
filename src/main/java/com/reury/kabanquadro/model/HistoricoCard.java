package com.reury.kabanquadro.model;


import java.time.LocalDateTime;

public class HistoricoCard {
    private int id;
    private int cardId;
    private int boardId;
    private TipoEvento tipoEvento;
    private LocalDateTime dataHoraEvento;
    private String descricaoEvento;
    private String detalhesJson;
    
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
    public int getBoardId() {
        return boardId;
    }
    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }
    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }
    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }
    public LocalDateTime getDataHoraEvento() {
        return dataHoraEvento;
    }
    public void setDataHoraEvento(LocalDateTime dataHoraEvento) {
        this.dataHoraEvento = dataHoraEvento;
    }
    public String getDescricaoEvento() {
        return descricaoEvento;
    }
    public void setDescricaoEvento(String descricaoEvento) {
        this.descricaoEvento = descricaoEvento;
    }
    public String getDetalhesJson() {
        return detalhesJson;
    }
    public void setDetalhesJson(String detalhesJson) {
        this.detalhesJson = detalhesJson;
    }

    // getters e setters
}