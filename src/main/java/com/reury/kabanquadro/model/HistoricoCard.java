package com.reury.kabanquadro.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class HistoricoCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    private LocalDateTime dataHoraEvento;
    private String descricaoEvento;
    private String detalhesJson;

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Card getCard() { return card; }
    public void setCard(Card card) { this.card = card; }
    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board; }
    public TipoEvento getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(TipoEvento tipoEvento) { this.tipoEvento = tipoEvento; }
    public LocalDateTime getDataHoraEvento() { return dataHoraEvento; }
    public void setDataHoraEvento(LocalDateTime dataHoraEvento) { this.dataHoraEvento = dataHoraEvento; }
    public String getDescricaoEvento() { return descricaoEvento; }
    public void setDescricaoEvento(String descricaoEvento) { this.descricaoEvento = descricaoEvento; }
    public String getDetalhesJson() { return detalhesJson; }
    public void setDetalhesJson(String detalhesJson) { this.detalhesJson = detalhesJson; }
}