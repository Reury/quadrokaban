package com.reury.kabanquadro.model;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
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


}