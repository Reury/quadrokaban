package com.reury.kabanquadro.model;

import jakarta.persistence.*;

@Entity
public class RelatorioBloqueios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    private int quantidadeBloqueios;
    private int tempoTotalBloqueado;
    private String motivosBloqueioJson;
    private String motivosDesbloqueioJson;

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board; }
    public Card getCard() { return card; }
    public void setCard(Card card) { this.card = card; }
    public int getQuantidadeBloqueios() { return quantidadeBloqueios; }
    public void setQuantidadeBloqueios(int quantidadeBloqueios) { this.quantidadeBloqueios = quantidadeBloqueios; }
    public int getTempoTotalBloqueado() { return tempoTotalBloqueado; }
    public void setTempoTotalBloqueado(int tempoTotalBloqueado) { this.tempoTotalBloqueado = tempoTotalBloqueado; }
    public String getMotivosBloqueioJson() { return motivosBloqueioJson; }
    public void setMotivosBloqueioJson(String motivosBloqueioJson) { this.motivosBloqueioJson = motivosBloqueioJson; }
    public String getMotivosDesbloqueioJson() { return motivosDesbloqueioJson; }
    public void setMotivosDesbloqueioJson(String motivosDesbloqueioJson) { this.motivosDesbloqueioJson = motivosDesbloqueioJson; }
}