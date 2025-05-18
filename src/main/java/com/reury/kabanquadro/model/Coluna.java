package com.reury.kabanquadro.model;


public class Coluna {
    private int id;
    private String nome;
    private int ordem;
    private TipoColuna tipo;
    private int boardId;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getOrdem() {
        return ordem;
    }
    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }
    public TipoColuna getTipo() {
        return tipo;
    }
    public void setTipo(TipoColuna tipo) {
        this.tipo = tipo;
    }
    public int getBoardId() {
        return boardId;
    }
    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    // getters e setters
}