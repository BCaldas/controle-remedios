package com.example.aluno.appremedio.model;

import java.io.Serializable;

/**
 * Created by bruno on 28/09/2017.
 */

public class Tratamento implements Serializable {

    private Integer id, diasTratamento;
    private Usuario usuario;
    private Remedio remedio;
    private String dosagem;

    public Tratamento() {
    }

    public Tratamento(Integer id, Integer diasTratamento, Usuario usuario, Remedio remedio, String dosagem) {
        this.id = id;
        this.diasTratamento = diasTratamento;
        this.usuario = usuario;
        this.remedio = remedio;
        this.dosagem = dosagem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDiasTratamento() {
        return diasTratamento;
    }

    public void setDiasTratamento(Integer diasTratamento) {
        this.diasTratamento = diasTratamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Remedio getRemedio() {
        return remedio;
    }

    public void setRemedio(Remedio remedio) {
        this.remedio = remedio;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }
}
