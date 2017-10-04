package br.com.brunoedalcilene.horadoremdio.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.brunoedalcilene.horadoremdio.database.Database;
import br.com.brunoedalcilene.horadoremdio.model.Agenda;
import br.com.brunoedalcilene.horadoremdio.model.ETipoDosagem;
import br.com.brunoedalcilene.horadoremdio.model.Paciente;
import br.com.brunoedalcilene.horadoremdio.model.Remedio;
import br.com.brunoedalcilene.horadoremdio.model.Tratamento;

/**
 * Created by bruno on 30/09/2017.
 */

public class AgendaDao extends BaseDao {
    SimpleDateFormat sdf;

    public AgendaDao(Context context) {
        super(context);
        sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    public void inserir(Agenda agenda){
        open();
        try {

            ContentValues cv = new ContentValues();
            cv.put(Database.AGENDA_TRATAMENTO, agenda.getTratamento().getId());
            cv.put(Database.AGENDA_DATA_HORA, sdf.format(agenda.getDataHoraConsumo()));
            cv.put(Database.AGENDA_PRONTO, agenda.getPronto());

            c.insert(Database.TABELA_AGENDA,null,cv);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            close();
        }
    }
    public List<Agenda> obterTodos(){
        open();
        try {
            String sql = "SELECT a.*, t.*, p.*, r.* FROM " + Database.TABELA_AGENDA + " AS a" +
                    " INNER JOIN " + Database.TABELA_TRATAMENTO +
                    " AS t ON a."+Database.AGENDA_TRATAMENTO+ " = t."+Database.TRATAMENTO_ID +
                    " INNER JOIN " + Database.TABELA_PACIENTE +
                    " AS p ON t."+Database.TRATAMENTO_PACIENTE+ " = p."+Database.TRATAMENTO_PACIENTE +
                    " INNER JOIN " + Database.TABELA_REMEDIO +
                    " AS r ON t."+Database.TRATAMENTO_REMEDIO+ " = r."+Database.REMEDIO_ID;

            Cursor cur = c.rawQuery(sql,null);

            List<Agenda> agendas = new ArrayList<>();

                while(cur.moveToNext()){

                    Paciente p = new Paciente();
                    Remedio r = new Remedio();
                    Tratamento t = new Tratamento();
                    Agenda a = new Agenda();

                    p.setId(cur.getInt(cur.getColumnIndex(Database.PACIENTE_ID)));
                    p.setNome(cur.getString(cur.getColumnIndex(Database.PACIENTE_NOME)));

                    r.setId(cur.getInt(cur.getColumnIndex(Database.REMEDIO_ID)));
                    r.setNome(cur.getString(cur.getColumnIndex(Database.REMEDIO_NOME)));
                    r.setDescricao(cur.getString(cur.getColumnIndex(Database.REMEDIO_DESC)));

                    t.setId( cur.getInt( cur.getColumnIndex(Database.TRATAMENTO_ID ) ) );
                    t.setPaciente(p);

                    t.setRemedio(r);
                    t.setDosagem(cur.getDouble(cur.getColumnIndex(Database.TRATAMENTO_DOSAGEM)));
                    t.setPeriodoDias(cur.getInt(cur.getColumnIndex(Database.TRATAMENTO_DIAS)));
                    t.setPeriodoHoras(cur.getInt(cur.getColumnIndex(Database.TRATAMENTO_HORAS)));
                    t.setTipoDosagem(ETipoDosagem.valueOf(
                            cur.getString(cur.getColumnIndex(Database.TRATAMENTO_TIPO_DOSAGEM))));

                    a.setId(cur.getInt(cur.getColumnIndex(Database.AGENDA_ID)));
                    a.setDataHoraConsumo(sdf.parse(cur.getString(cur.getColumnIndex(Database.AGENDA_DATA_HORA))));
                    a.setPronto((cur.getInt(cur.getColumnIndex(Database.AGENDA_PRONTO)))==1?Boolean.TRUE:Boolean.FALSE);
                    a.setTratamento(t);

                agendas.add(a);
            }
            return agendas;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }
    public List<Agenda> obterPorNome(String nome){
        return null;
    }
    public Agenda obterPorId(int id){
        return null;
    }
}
