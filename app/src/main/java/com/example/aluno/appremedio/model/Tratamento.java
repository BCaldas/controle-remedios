package com.example.aluno.appremedio.model;

import java.io.Serializable;

/**
 * Created by bruno on 28/09/2017.
 */

public class Tratamento implements Serializable {

    private Integer id, periodoDias, periodoHoras;
    private Usuario usuario;
    private Remedio remedio;
    private String dosagem;

    public Tratamento() {
    }

    public Tratamento(Integer id, Integer periodoDias, Usuario usuario, Remedio remedio, String dosagem, Integer periodoHoras) {
        this.id = id;
        this.periodoDias = periodoDias;
        this.periodoHoras = periodoHoras;
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

    public Integer getPeriodoDias() {
        return periodoDias;
    }

    public void setPeriodoDias(Integer periodoDias) {
        this.periodoDias = periodoDias;
    }

    public Integer getPeriodoHoras() {
        return periodoHoras;
    }

    public void setPeriodoHoras(Integer periodoHoras) {
        this.periodoHoras = periodoHoras;
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
