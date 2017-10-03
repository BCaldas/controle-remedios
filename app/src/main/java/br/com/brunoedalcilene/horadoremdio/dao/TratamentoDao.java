package br.com.brunoedalcilene.horadoremdio.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import br.com.brunoedalcilene.horadoremdio.database.Database;
import br.com.brunoedalcilene.horadoremdio.model.ETipoDosagem;
import br.com.brunoedalcilene.horadoremdio.model.Paciente;
import br.com.brunoedalcilene.horadoremdio.model.Remedio;
import br.com.brunoedalcilene.horadoremdio.model.Tratamento;

/**
 * Created by bruno on 30/09/2017.
 */

public class TratamentoDao extends BaseDao{

    public TratamentoDao(Context context) {
        super(context);
    }

    public void inserir(Tratamento tratamento){
        open();
        try {
            ContentValues cv = new ContentValues();
            cv.put(Database.TRATAMENTO_PACIENTE, tratamento.getPaciente().getId());
            cv.put(Database.TRATAMENTO_REMEDIO, tratamento.getRemedio().getId());
            cv.put(Database.TRATAMENTO_DIAS, tratamento.getPeriodoDias());
            cv.put(Database.TRATAMENTO_HORAS, tratamento.getPeriodoHoras());
            cv.put(Database.TRATAMENTO_DOSAGEM, tratamento.getDosagem());
            cv.put(Database.TRATAMENTO_TIPO_DOSAGEM, tratamento.getTipoDosagem().toString());

            c.insert(Database.TABELA_TRATAMENTO,null,cv);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            close();
        }
    }

    public List<Tratamento> obterTodos(){
        open();
        try {
            String sql = "SELECT t.*, p.*, r.* FROM " + Database.TABELA_TRATAMENTO + " AS t" +
                    " INNER JOIN " + Database.TABELA_PACIENTE +
                    " AS p ON t."+Database.TRATAMENTO_PACIENTE+ " = p."+Database.PACIENTE_ID +
                    " INNER JOIN " + Database.TABELA_REMEDIO +
                    " AS r ON t."+Database.TRATAMENTO_REMEDIO+ " = r."+Database.REMEDIO_ID;

            Cursor cur = c.rawQuery(sql,null);

            List<Tratamento> tratamentos = new ArrayList<>();

            while(cur.moveToNext()){
                Tratamento t = new Tratamento();
                t.setId( cur.getInt( cur.getColumnIndex(Database.TRATAMENTO_ID ) ) );
                t.setPaciente(new Paciente(
                        cur.getInt( cur.getColumnIndex(Database.PACIENTE_ID )),
                        cur.getString(cur.getColumnIndex(Database.PACIENTE_NOME))
                ));
                t.setRemedio(new Remedio(
                        cur.getInt(cur.getColumnIndex(Database.REMEDIO_ID)),
                        cur.getString(cur.getColumnIndex(Database.REMEDIO_DESC)),
                        cur.getString(cur.getColumnIndex(Database.REMEDIO_NOME))
                        ));
                t.setDosagem(cur.getDouble(cur.getColumnIndex(Database.TRATAMENTO_DOSAGEM)));
                t.setPeriodoDias(cur.getInt(cur.getColumnIndex(Database.TRATAMENTO_DIAS)));
                t.setPeriodoHoras(cur.getInt(cur.getColumnIndex(Database.TRATAMENTO_HORAS)));
                t.setTipoDosagem(ETipoDosagem.valueOf(
                        cur.getString(cur.getColumnIndex(Database.TRATAMENTO_TIPO_DOSAGEM))));

                tratamentos.add(t);
            }
            return tratamentos;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }

    public List<Tratamento> obterPorNome(String nome){
        return null;
    }
    public Tratamento obterPorId(int id){
        return null;
    }
}
