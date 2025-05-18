package com.reury.kabanquadro.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Bloqueio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    private LocalDateTime dataHoraBloqueio;
    private LocalDateTime dataHoraDesbloqueio;
    private String motivoBloqueio;
    private String motivoDesbloqueio;
    private int tempoBloqueado;

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Card getCard() { return card; }
    public void setCard(Card card) { this.card = card; }
    public LocalDateTime getDataHoraBloqueio() { return dataHoraBloqueio; }
    public void setDataHoraBloqueio(LocalDateTime dataHoraBloqueio) { this.dataHoraBloqueio = dataHoraBloqueio; }
    public LocalDateTime getDataHoraDesbloqueio() { return dataHoraDesbloqueio; }
    public void setDataHoraDesbloqueio(LocalDateTime dataHoraDesbloqueio) { this.dataHoraDesbloqueio = dataHoraDesbloqueio; }
    public String getMotivoBloqueio() { return motivoBloqueio; }
    public void setMotivoBloqueio(String motivoBloqueio) { this.motivoBloqueio = motivoBloqueio; }
    public String getMotivoDesbloqueio() { return motivoDesbloqueio; }
    public void setMotivoDesbloqueio(String motivoDesbloqueio) { this.motivoDesbloqueio = motivoDesbloqueio; }
    public int getTempoBloqueado() { return tempoBloqueado; }
    public void setTempoBloqueado(int tempoBloqueado) { this.tempoBloqueado = tempoBloqueado; }
}