package com.reury.kabanquadro.model;


import java.time.LocalDateTime;

public class Card {
    private int id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private boolean bloqueado;
    private int ultimaColunaId;
    private LocalDateTime dataEntradaColuna;
    private int colunaId;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public boolean isBloqueado() {
        return bloqueado;
    }
    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
    public int getUltimaColunaId() {
        return ultimaColunaId;
    }
    public void setUltimaColunaId(int ultimaColunaId) {
        this.ultimaColunaId = ultimaColunaId;
    }
    public LocalDateTime getDataEntradaColuna() {
        return dataEntradaColuna;
    }
    public void setDataEntradaColuna(LocalDateTime dataEntradaColuna) {
        this.dataEntradaColuna = dataEntradaColuna;
    }
    public int getColunaId() {
        return colunaId;
    }
    public void setColunaId(int colunaId) {
        this.colunaId = colunaId;
    }

    // getters e setters
}