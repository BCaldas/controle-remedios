package br.com.brunoedalcilene.horadoremdio;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.brunoedalcilene.horadoremdio.dao.PacienteDao;
import br.com.brunoedalcilene.horadoremdio.dao.RemedioDao;
import br.com.brunoedalcilene.horadoremdio.model.Paciente;
import br.com.brunoedalcilene.horadoremdio.model.Remedio;

public class CadastroTratamentoActivity extends AppCompatActivity {

    Spinner spnPaciente, spnRemedio;
    List<Paciente> pacientes;
    List<Remedio> remedios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastro_tratamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();
        preencheSpinnerPaciente();
        preencheSpinnerRemedio();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void binding() {
        spnPaciente = (Spinner) findViewById(R.id.spnTratamentoPaciente);
        spnRemedio = (Spinner) findViewById(R.id.spnTratamentoRemedio);
    }

    private void preencheSpinnerPaciente() {
        pacientes = new PacienteDao(getApplicationContext()).obterTodos();

        List<String> list = new ArrayList<>();

        for(Paciente p: pacientes){
            list.add(p.getNome());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPaciente.setAdapter(dataAdapter);
    }

    private void preencheSpinnerRemedio() {
        remedios = new RemedioDao(getApplicationContext()).obterTodos();

        List<String> list = new ArrayList<>();

        for(Remedio r: remedios){
            list.add(r.getNome());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRemedio.setAdapter(dataAdapter);
    }
}
