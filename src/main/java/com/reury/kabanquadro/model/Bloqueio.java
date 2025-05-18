package com.reury.kabanquadro.model;

import java.time.LocalDateTime;

public class Bloqueio {
    private int id;
    private int cardId;
    private LocalDateTime dataHoraBloqueio;
    private LocalDateTime dataHoraDesbloqueio;
    private String motivoBloqueio;
    private String motivoDesbloqueio;
    private int tempoBloqueado;
    
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
    public LocalDateTime getDataHoraBloqueio() {
        return dataHoraBloqueio;
    }
    public void setDataHoraBloqueio(LocalDateTime dataHoraBloqueio) {
        this.dataHoraBloqueio = dataHoraBloqueio;
    }
    public LocalDateTime getDataHoraDesbloqueio() {
        return dataHoraDesbloqueio;
    }
    public void setDataHoraDesbloqueio(LocalDateTime dataHoraDesbloqueio) {
        this.dataHoraDesbloqueio = dataHoraDesbloqueio;
    }
    public String getMotivoBloqueio() {
        return motivoBloqueio;
    }
    public void setMotivoBloqueio(String motivoBloqueio) {
        this.motivoBloqueio = motivoBloqueio;
    }
    public String getMotivoDesbloqueio() {
        return motivoDesbloqueio;
    }
    public void setMotivoDesbloqueio(String motivoDesbloqueio) {
        this.motivoDesbloqueio = motivoDesbloqueio;
    }
    public int getTempoBloqueado() {
        return tempoBloqueado;
    }
    public void setTempoBloqueado(int tempoBloqueado) {
        this.tempoBloqueado = tempoBloqueado;
    }

    // getters e setters
}