package br.com.brunoedalcilene.horadoremdio.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.com.brunoedalcilene.horadoremdio.database.Database;

/**
 * Created by bruno on 02/10/2017.
 */

public abstract class BaseDao {

    Context context;
    Database banco;
    SQLiteDatabase c;

    public BaseDao(Context context) {
        this.context = context;
        banco = new Database(context);
    }

    protected void open(){
        c = banco.getWritableDatabase();
    }

    protected void close(){
        c.close();
        banco.close();
    }
}
