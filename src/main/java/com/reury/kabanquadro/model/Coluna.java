package com.reury.kabanquadro.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Coluna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private int ordem;

    @Enumerated(EnumType.STRING)
    private TipoColuna tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "coluna", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Card> cards = new HashSet<>();

    private boolean arquivado = false;

    public Coluna(String nome, int ordem, TipoColuna tipo, Board board) {
        this.nome = nome;
        this.ordem = ordem;
        this.tipo = tipo;
        this.board = board;
    }
}