package br.com.brunoedalcilene.horadoremdio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.brunoedalcilene.horadoremdio.dao.PacienteDao;
import br.com.brunoedalcilene.horadoremdio.dao.RemedioDao;
import br.com.brunoedalcilene.horadoremdio.dao.TratamentoDao;
import br.com.brunoedalcilene.horadoremdio.model.ETipoDosagem;
import br.com.brunoedalcilene.horadoremdio.model.Paciente;
import br.com.brunoedalcilene.horadoremdio.model.Remedio;
import br.com.brunoedalcilene.horadoremdio.model.Tratamento;
import br.com.brunoedalcilene.horadoremdio.util.ActivityUtil;

public class CadastroTratamentoActivity extends AppCompatActivity {

    Spinner spnPaciente, spnRemedio, spnTipoDosagem;
    EditText dosagem, horas, dias;
    Button btnSalvar, btnLimpar;
    List<Paciente> pacientes;
    List<Remedio> remedios;
    Tratamento tratamento;
    ActivityUtil util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastro_tratamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();
        preencheSpinnerPaciente();
        preencheSpinnerRemedio();
        preencherSpinnerTipoDosagem();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tratamento = (Tratamento) getIntent().getSerializableExtra("tratamento");

        if (tratamento != null) {
            util.bloquearElementos(findViewById(android.R.id.content),false);

        } else {

            btnSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    tratamento = new Tratamento();
                    tratamento.setPaciente(pacientes.get(spnPaciente.getSelectedItemPosition()));
                    tratamento.setRemedio(remedios.get(spnRemedio.getSelectedItemPosition()));
                    tratamento.setDosagem(Double.parseDouble(dosagem.getText().toString()));
                    tratamento.setTipoDosagem(ETipoDosagem.valueOf(spnTipoDosagem.getSelectedItem().toString()));
                    tratamento.setPeriodoHoras(Integer.parseInt(horas.getText().toString()));
                    tratamento.setPeriodoDias(Integer.parseInt(dias.getText().toString()));

                    new TratamentoDao(getApplicationContext())
                            .inserir(tratamento);
                    Toast.makeText(getApplicationContext(), "Tratamento Cadastrado com sucesso", Toast.LENGTH_LONG).show();
                    finish();

//                    if (nome.getText().toString().isEmpty()) {
//                        Toast.makeText(getApplicationContext(),"O campo Nome é obrigatório.",Toast.LENGTH_SHORT).show();
//                    } else {
//
//                    }
                }
            });

//            btnLimpar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    nome.setText("");
//                    descricao.setText("");
//                }
//            });
        }
    }

    private void binding() {
        spnPaciente = (Spinner) findViewById(R.id.spnTratamentoPaciente);
        spnRemedio = (Spinner) findViewById(R.id.spnTratamentoRemedio);
        dosagem = (EditText) findViewById(R.id.txtTratamentoDosagem);
        spnTipoDosagem = (Spinner) findViewById(R.id.spnTratamentoTipoDosagem);
        horas = (EditText) findViewById(R.id.txtTratamentoHoras);
        dias = (EditText) findViewById(R.id.txtTratamentoDias);
        btnSalvar = (Button) findViewById(R.id.btnTratamentoSalvar);
        btnLimpar = (Button) findViewById(R.id.btnTratamentoLimpar);
        util = new ActivityUtil(this,this);
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

    private void preencherSpinnerTipoDosagem() {
        List<String> list = new ArrayList<>();

        list.add(ETipoDosagem.Comprimido.toString());
        list.add(ETipoDosagem.Mililitros.toString());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoDosagem.setAdapter(dataAdapter);
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
}
