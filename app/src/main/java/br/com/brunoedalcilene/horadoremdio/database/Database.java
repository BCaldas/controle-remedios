package br.com.brunoedalcilene.horadoremdio.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bruno on 29/09/2017.
 */

public class Database extends SQLiteOpenHelper {

    public static final String dbName = "remedios.db";
    public static final int dbVersion = 1;
    Context context;

    public Database(Context context) {
        super(context, dbName, null, dbVersion);
        this.context = context;
    }

    //Tabela Remédios
    public static final String TABELA_REMEDIO = "remedio";
    public static final String REMEDIO_ID = "id_remedio";
    public static final String REMEDIO_DESC = "descricao";
    public static final String REMEDIO_NOME = "nome_remedio";

    //Tabela Usuário
    public static final String TABELA_PACIENTE = "paciente";
    public static final String PACIENTE_ID = "id_paciente";
    public static final String PACIENTE_NOME = "nome_paciente";

    //Tabela Tratamento
    public static final String TABELA_TRATAMENTO = "tratamento";
    public static final String TRATAMENTO_ID = "id_tratamento";
    public static final String TRATAMENTO_PACIENTE = "id_paciente";
    public static final String TRATAMENTO_REMEDIO ="id_remedio";
    public static final String TRATAMENTO_DIAS = "periodo_dias";
    public static final String TRATAMENTO_HORAS = "periodo_horas";
    public static final String TRATAMENTO_DOSAGEM = "dosagem";
    public static final String TRATAMENTO_TIPO_DOSAGEM = "tipo_dosagem";


    //Tabela Agenda
    public static final String TABELA_AGENDA = "agenda";
    public static final String AGENDA_ID = "id_agenda";
    public static final String AGENDA_TRATAMENTO = "id_tratamento";
    public static final String AGENDA_DATA_HORA = "data_hora";
    public static final String AGENDA_CHECK = "pronto";

    private static final String CREATE_TABLE_REMEDIO = "CREATE TABLE " + TABELA_REMEDIO + "("+
            " " + REMEDIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " " + REMEDIO_NOME + " text NOT NULL," +
            " " + REMEDIO_DESC + " text)";

    private static final String CREATE_TABLE_PACIENTE = "CREATE TABLE " + TABELA_PACIENTE + "("+
            " " + PACIENTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " " + PACIENTE_NOME + " text NOT NULL)";

    private static final String CREATE_TABLE_TRATAMENTO = "CREATE TABLE " + TABELA_TRATAMENTO + "("+
            " " + TRATAMENTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " " + TRATAMENTO_PACIENTE + " INTEGER NOT NULL," +
            " " + TRATAMENTO_REMEDIO + " INTEGER NOT NULL," +
            " " + TRATAMENTO_DIAS + " INTEGER NOT NULL," +
            " " + TRATAMENTO_HORAS + " INTEGER NOT NULL," +
            " " + TRATAMENTO_DOSAGEM + " REAL," +
            " " + TRATAMENTO_TIPO_DOSAGEM + " text NOT NULL," +
            " FOREIGN KEY("+ TRATAMENTO_PACIENTE + ")" + " REFERENCES " + TABELA_PACIENTE + "(" + PACIENTE_ID + ")" +
            " FOREIGN KEY("+ TRATAMENTO_REMEDIO + ")" + " REFERENCES " + TABELA_REMEDIO + "(" + REMEDIO_ID + ")" +
            ")";

    private static final String CREATE_TABLE_AGENDA = "CREATE TABLE " + TABELA_AGENDA + "("+
            " " + AGENDA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " " + AGENDA_TRATAMENTO + " INTEGER NOT NULL," +
            " " + AGENDA_DATA_HORA + " text NOT NULL," +
            " " + AGENDA_CHECK + " INTEGER NOT NULL," +
            " FOREIGN KEY("+ AGENDA_TRATAMENTO + ")" + " REFERENCES " + TABELA_TRATAMENTO + "(" + TRATAMENTO_ID + ")" +
            ")";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_REMEDIO);
        sqLiteDatabase.execSQL(CREATE_TABLE_PACIENTE);
        sqLiteDatabase.execSQL(CREATE_TABLE_TRATAMENTO);
        sqLiteDatabase.execSQL(CREATE_TABLE_AGENDA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE "
                +TABELA_REMEDIO+
                ","+ TABELA_PACIENTE +
                ","+TABELA_TRATAMENTO+
                ","+TABELA_AGENDA);
        onCreate(sqLiteDatabase);
    }
}
