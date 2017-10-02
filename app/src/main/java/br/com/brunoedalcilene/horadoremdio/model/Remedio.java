package br.com.brunoedalcilene.horadoremdio.model;

import java.io.Serializable;

/**
 * Created by bruno on 28/09/2017.
 */

public class Remedio implements Serializable {

    private Integer id;
    private String descricao, nome;

    public Remedio() {

    }

    public Remedio(Integer id, String descricao, String nome) {
        this.id = id;
        this.descricao = descricao;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
