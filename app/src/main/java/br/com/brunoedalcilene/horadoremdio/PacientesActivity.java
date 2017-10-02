package br.com.brunoedalcilene.horadoremdio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.brunoedalcilene.horadoremdio.dao.PacienteDao;
import br.com.brunoedalcilene.horadoremdio.dao.RemedioDao;
import br.com.brunoedalcilene.horadoremdio.model.Paciente;
import br.com.brunoedalcilene.horadoremdio.model.Remedio;
import br.com.brunoedalcilene.horadoremdio.util.ActivityUtil;

public class PacientesActivity extends AppCompatActivity {

    ListView lstPacientes;
    FloatingActionButton fab;
    PacientesActivity activity;
    Toolbar toolbar;
    List<Paciente> pacientes;
    ActivityUtil util;
    private static int CADASTRO_PACIENTES = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes);
        binding();
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        preencherListView(null);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util.chamarActivity(CadastroPacienteActivity.class,CADASTRO_PACIENTES,null,null);
            }
        });

        lstPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                util.chamarActivity(CadastroPacienteActivity.class,CADASTRO_PACIENTES,"paciente",pacientes.get(i));
            }
        });
    }

    private void binding() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        lstPacientes = (ListView) findViewById(R.id.lstPacientes);
        activity = (PacientesActivity) this;
        util = new ActivityUtil(getApplicationContext(), activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:break;
        }
        return true;
    }

    private void preencherListView(String nome) {
        if (nome == null) {
            pacientes = new PacienteDao(getApplicationContext()).obterTodos();
        }else{
            pacientes = new PacienteDao(getApplicationContext()).obterPorNome(nome);
        }

        List<String> list = new ArrayList<>();
        for(Paciente p: pacientes){
            list.add(p.getNome());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        lstPacientes.setAdapter(dataAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CADASTRO_PACIENTES){
            preencherListView(null);
        }
    }
}
