package br.com.brunoedalcilene.horadoremdio.model;

import java.io.Serializable;

/**
 * Created by bruno on 28/09/2017.
 */

public class Tratamento implements Serializable {

    private Integer id, periodoDias, periodoHoras;
    private Paciente paciente;
    private Remedio remedio;
    private Double dosagem;
    private ETipoDosagem tipoDosagem;

    public Tratamento() {
    }

    public Tratamento(Integer id, Integer periodoDias, Paciente paciente, Remedio remedio, Double dosagem, Integer periodoHoras, ETipoDosagem tipoDosagem) {
        this.id = id;
        this.periodoDias = periodoDias;
        this.periodoHoras = periodoHoras;
        this.paciente = paciente;
        this.remedio = remedio;
        this.dosagem = dosagem;
        this.tipoDosagem = tipoDosagem;
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

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Remedio getRemedio() {
        return remedio;
    }

    public void setRemedio(Remedio remedio) {
        this.remedio = remedio;
    }

    public Double getDosagem() {
        return dosagem;
    }

    public void setDosagem(Double dosagem) {
        this.dosagem = dosagem;
    }

    public ETipoDosagem getTipoDosagem() {
        return tipoDosagem;
    }

    public void setTipoDosagem(ETipoDosagem tipoDosagem) {
        this.tipoDosagem = tipoDosagem;
    }
}
