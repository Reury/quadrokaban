package com.reury.kabanquadro.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
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

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public boolean isBloqueado() { return bloqueado; }
    public void setBloqueado(boolean bloqueado) { this.bloqueado = bloqueado; }
    public boolean isArquivado() { return arquivado; }
    public void setArquivado(boolean arquivado) { this.arquivado = arquivado; }
    public Coluna getColuna() { return coluna; }
    public void setColuna(Coluna coluna) { this.coluna = coluna; }
    public Long getUltimaColunaId() { return ultimaColunaId; }
    public void setUltimaColunaId(Long ultimaColunaId) { this.ultimaColunaId = ultimaColunaId; }
    public LocalDateTime getDataEntradaColuna() { return dataEntradaColuna; }
    public void setDataEntradaColuna(LocalDateTime dataEntradaColuna) { this.dataEntradaColuna = dataEntradaColuna; }
}