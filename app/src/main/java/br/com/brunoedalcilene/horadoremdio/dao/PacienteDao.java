package br.com.brunoedalcilene.horadoremdio.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

import br.com.brunoedalcilene.horadoremdio.database.Database;
import br.com.brunoedalcilene.horadoremdio.model.Paciente;

/**
 * Created by bruno on 30/09/2017.
 */

public class PacienteDao extends BaseDao {

    public PacienteDao(Context context) {
        super(context);
    }

    public void inserir(Paciente paciente){
        open();
        try {
            ContentValues cv = new ContentValues();
            cv.put(Database.PACIENTE_NOME, paciente.getNome());

            c.insert(Database.TABELA_PACIENTE,null,cv);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            close();
        }
    }
    public List<Paciente> obterTodos(){
        open();
        try {
            String sql = "select * from "+Database.TABELA_PACIENTE;
            Cursor cur = c.rawQuery(sql,null);

            List<Paciente> pacientes = new ArrayList<>();

            while(cur.moveToNext()){
                Paciente p = new Paciente();
                p.setId( cur.getInt( cur.getColumnIndex(Database.PACIENTE_ID ) ) );
                p.setNome( cur.getString( cur.getColumnIndex(Database.PACIENTE_NOME ) ) );

                pacientes.add(p);
            }
            return pacientes;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }
    public List<Paciente> obterPorNome(String nome){
        return null;
    }
    public Paciente obterPorId(int id){
        return null;
    }
}
