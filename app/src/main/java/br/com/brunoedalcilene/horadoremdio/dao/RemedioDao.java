package br.com.brunoedalcilene.horadoremdio.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import br.com.brunoedalcilene.horadoremdio.database.Database;
import br.com.brunoedalcilene.horadoremdio.model.Remedio;

/**
 * Created by bruno on 30/09/2017.
 */

public class RemedioDao {

    Context context;
    Database banco;
    SQLiteDatabase c;

    public RemedioDao(Context context) {
        this.context = context;
        banco = new Database(context);
    }

    private void open(){
        c = banco.getWritableDatabase();
    }
    
    private void close(){
        c.close();
        banco.close();
    }

    public void inserir(Remedio remedio){
        open();
        try {
            ContentValues cv = new ContentValues();
            cv.put(Database.REMEDIO_NOME, remedio.getNome());
            cv.put(Database.REMEDIO_DESC, remedio.getDescricao());

            c.insert(Database.TABELA_REMEDIO,null,cv);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            close();
        }
    }
    public List<Remedio> obterTodos(){
        open();
        try {
            String sql = "select * from "+Database.TABELA_REMEDIO;
            Cursor cur = c.rawQuery(sql,null);

            List<Remedio> remedios = new ArrayList<>();

            while(cur.moveToNext()){
                Remedio r = new Remedio();
                r.setId( cur.getInt( cur.getColumnIndex(Database.REMEDIO_ID ) ) );
                r.setNome( cur.getString( cur.getColumnIndex(Database.REMEDIO_NOME ) ) );
                r.setDescricao( cur.getString( cur.getColumnIndex(Database.REMEDIO_DESC ) ) );

                remedios.add(r);
            }
            return remedios;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }
    public List<Remedio> obterPorNome(String nome){
        return null;
    }
    public Remedio obterPorId(int id){
        return null;
    }
}
