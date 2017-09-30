package br.com.brunoedalcilene.horadoremdio.model;

import java.io.Serializable;

/**
 * Created by bruno on 28/09/2017.
 */

public class Remedio implements Serializable {

    private Integer id, miligramas;
    private String descricao, nome;
    private ETipo tipo;

    public Remedio() {

    }

    public Remedio(Integer id, Integer miligramas, String descricao, String nome, ETipo tipo) {
        this.id = id;
        this.miligramas = miligramas;
        this.descricao = descricao;
        this.nome = nome;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMiligramas() {
        return miligramas;
    }

    public void setMiligramas(Integer miligramas) {
        this.miligramas = miligramas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ETipo getTipo() {
        return tipo;
    }

    public void setTipo(ETipo tipo) {
        this.tipo = tipo;
    }
}