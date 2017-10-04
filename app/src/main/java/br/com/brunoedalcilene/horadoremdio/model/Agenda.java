package br.com.brunoedalcilene.horadoremdio.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by bruno on 28/09/2017.
 */

public class Agenda implements Serializable {

    private Integer id;
    private Tratamento tratamento;
    private Date dataHoraConsumo;
    private Boolean check;

    public Agenda(Integer id, Tratamento tratamento, Date dataHoraConsumo, Boolean check) {
        this.id = id;
        this.tratamento = tratamento;
        this.dataHoraConsumo = dataHoraConsumo;
        this.check = check;
    }

    public Agenda() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tratamento getTratamento() {
        return tratamento;
    }

    public void setTratamento(Tratamento tratamento) {
        this.tratamento = tratamento;
    }

    public Date getDataHoraConsumo() {
        return dataHoraConsumo;
    }

    public void setDataHoraConsumo(Date dataHoraConsumo) {
        this.dataHoraConsumo = dataHoraConsumo;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
