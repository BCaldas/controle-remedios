package br.com.brunoedalcilene.horadoremdio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    Button share;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tratamento = (Tratamento) getIntent().getSerializableExtra("tratamento");

        if (tratamento != null) {

            getSupportActionBar().setTitle(tratamento.getPaciente().getNome() +
                    " - " + tratamento.getRemedio().getNome());
            selecionarPaciente(tratamento.getPaciente());
            selecionarRemedio(tratamento.getRemedio());
            dosagem.setText(tratamento.getDosagem().toString());
            spnTipoDosagem.setSelection(tratamento.getTipoDosagem()==ETipoDosagem.Comprimido?0:1);
            horas.setText(tratamento.getPeriodoHoras().toString());
            dias.setText(tratamento.getPeriodoDias().toString());

            util.bloquearElementos(findViewById(android.R.id.content),false);
            share.setEnabled(true);

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String send = "Paciente: "+tratamento.getPaciente().getNome() +
                            " - Remédio: " +tratamento.getRemedio().getNome() +
                            " - Período Dias: " + tratamento.getPeriodoDias().toString() +
                            " - Intervalo de Horas: " + tratamento.getPeriodoHoras();

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, send);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
            });

        } else {
            preencheSpinnerPaciente();
            preencheSpinnerRemedio();
            preencherSpinnerTipoDosagem();
            share.setVisibility(View.INVISIBLE);

            btnSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (dosagem.getText().toString().isEmpty() ||
                            horas.getText().toString().isEmpty() ||
                            dias.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
                    } else {

                        tratamento = new Tratamento();
                        tratamento.setPaciente(pacientes.get(spnPaciente.getSelectedItemPosition()));
                        tratamento.setRemedio(remedios.get(spnRemedio.getSelectedItemPosition()));
                        tratamento.setDosagem(Double.parseDouble(dosagem.getText().toString()));
                        tratamento.setTipoDosagem(ETipoDosagem.valueOf(spnTipoDosagem.getSelectedItem().toString()));
                        tratamento.setPeriodoHoras(Integer.parseInt(horas.getText().toString()));
                        tratamento.setPeriodoDias(Integer.parseInt(dias.getText().toString()));

                        new TratamentoDao(getApplicationContext())
                                .inserir(tratamento);
                        Toast.makeText(getApplicationContext(), "Tratamento Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });

            btnLimpar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dosagem.setText("");
                    horas.setText("");
                    dias.setText("");
                }
            });
        }
    }

    private void binding() {
        share = (Button) findViewById(R.id.btnShare);
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

    private void selecionarPaciente(Paciente p) {

        List<String> list = new ArrayList<>();
        list.add(p.getNome());

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

    private void selecionarRemedio(Remedio r) {

        List<String> list = new ArrayList<>();

        list.add(r.getNome());

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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:break;
        }
        return true;
    }
}
