package com.reury.kabanquadro.model;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "coluna", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Card> cards;

    private boolean arquivado = false; // novo campo

    public Coluna(String nome, int ordem, TipoColuna tipo, Board board) {
    this.nome = nome;
    this.ordem = ordem;
    this.tipo = tipo;
    this.board = board;
    }

    public boolean isArquivado() { return arquivado; }
    public void setArquivado(boolean arquivado) { this.arquivado = arquivado; }
}