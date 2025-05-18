package com.reury.kabanquadro.model;

import jakarta.persistence.*;

@Entity
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

    public Coluna() {} // construtor padrão

    public Coluna(String nome, int ordem, TipoColuna tipo, Board board) {
    this.nome = nome;
    this.ordem = ordem;
    this.tipo = tipo;
    this.board = board;
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getOrdem() { return ordem; }
    public void setOrdem(int ordem) { this.ordem = ordem; }
    public TipoColuna getTipo() { return tipo; }
    public void setTipo(TipoColuna tipo) { this.tipo = tipo; }
    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board; }
    public java.util.List<Card> getCards() {
        return cards;
    }

    public void setCards(java.util.List<Card> cards) {
        this.cards = cards;
    }

    public boolean isArquivado() { return arquivado; }
    public void setArquivado(boolean arquivado) { this.arquivado = arquivado; }
}