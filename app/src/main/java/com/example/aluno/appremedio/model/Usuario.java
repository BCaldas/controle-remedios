package com.example.aluno.appremedio.model;

import java.io.Serializable;

/**
 * Created by bruno on 28/09/2017.
 */

public class Usuario implements Serializable {

    private Integer id;
    private String nome;

    public Usuario(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Usuario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
