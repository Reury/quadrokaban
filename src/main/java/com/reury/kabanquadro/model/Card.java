package com.reury.kabanquadro.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private boolean bloqueado;
    private boolean arquivado; // novo campo

    @ManyToOne
    @JoinColumn(name = "coluna_id")
    private Coluna coluna;

    private Long ultimaColunaId;
    private LocalDateTime dataEntradaColuna;


}