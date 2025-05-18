package com.reury.kabanquadro.model;

import java.time.LocalDateTime;
import java.util.List;

public class Board {
    private int id;
    private String nome;
    private LocalDateTime dataCriacao;
    private boolean ativo;
    private List<Coluna> colunas;
    
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
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    public List<Coluna> getColunas() {
        return colunas;
    }
    public void setColunas(List<Coluna> colunas) {
        this.colunas = colunas;
    }

    // getters e setters
}