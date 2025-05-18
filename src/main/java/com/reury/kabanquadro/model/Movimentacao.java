package com.reury.kabanquadro.model;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    private Long colunaOrigemId;
    private Long colunaDestinoId;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;

    @Convert(converter = DurationAttributeConverter.class)
    private Duration tempoNaColuna;

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Card getCard() { return card; }
    public void setCard(Card card) { this.card = card; }
    public Long getColunaOrigemId() { return colunaOrigemId; }
    public void setColunaOrigemId(Long colunaOrigemId) { this.colunaOrigemId = colunaOrigemId; }
    public Long getColunaDestinoId() { return colunaDestinoId; }
    public void setColunaDestinoId(Long colunaDestinoId) { this.colunaDestinoId = colunaDestinoId; }
    public LocalDateTime getDataHoraEntrada() { return dataHoraEntrada; }
    public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) { this.dataHoraEntrada = dataHoraEntrada; }
    public LocalDateTime getDataHoraSaida() { return dataHoraSaida; }
    public void setDataHoraSaida(LocalDateTime dataHoraSaida) { this.dataHoraSaida = dataHoraSaida; }
    public Duration getTempoNaColuna() { return tempoNaColuna; }
    public void setTempoNaColuna(Duration tempoNaColuna) { this.tempoNaColuna = tempoNaColuna; }
}