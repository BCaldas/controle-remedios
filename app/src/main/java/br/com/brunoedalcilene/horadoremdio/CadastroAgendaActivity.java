package br.com.brunoedalcilene.horadoremdio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.brunoedalcilene.horadoremdio.dao.AgendaDao;
import br.com.brunoedalcilene.horadoremdio.dao.TratamentoDao;
import br.com.brunoedalcilene.horadoremdio.database.Database;
import br.com.brunoedalcilene.horadoremdio.model.Agenda;
import br.com.brunoedalcilene.horadoremdio.model.Tratamento;
import br.com.brunoedalcilene.horadoremdio.util.ActivityUtil;

public class CadastroAgendaActivity extends AppCompatActivity {

    Spinner spnTratamento;
    CheckBox pronto;
    EditText data, hora;
    Button btnSalvar, btnLimpar;

    List<Tratamento> tratamentos;
    ActivityUtil util;
    Agenda agenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        agenda = (Agenda) getIntent().getSerializableExtra("agenda");

        if (agenda != null) {

            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
            sdfDate.applyPattern("dd/MM/yyyy");
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
            sdfTime.applyPattern("HH:mm");

            data.setText(sdfDate.format(agenda.getDataHoraConsumo()));
            hora.setText(sdfTime.format(agenda.getDataHoraConsumo()));
            selecionarTratamento(agenda.getTratamento());

            spnTratamento.setEnabled(false);
            data.setEnabled(false);
            hora.setEnabled(false);
            btnLimpar.setEnabled(false);

            btnSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    agenda.setPronto(pronto.isChecked());



                    new AgendaDao(getApplicationContext())
                            .alterar(agenda);
                    Toast.makeText(getApplicationContext(), "Lembrete atualizado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        } else {
            pronto.setVisibility(View.INVISIBLE);
            preencheSpinnerTratamento();
            btnSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (hora.getText().toString().isEmpty() ||
                            data.getText().toString().isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Por favor, preencha todos os campos.",Toast.LENGTH_SHORT).show();
                    } else {

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        agenda = new Agenda();
                        agenda.setTratamento(tratamentos.get(spnTratamento.getSelectedItemPosition()));
                        try {
                            agenda.setDataHoraConsumo(sdf.parse(data.getText().toString() + " " + hora.getText().toString()));
                        } catch (ParseException e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        agenda.setPronto(Boolean.FALSE);

                        try {
                            new AgendaDao(getApplicationContext())
                                    .inserir(agenda);
                            Toast.makeText(getApplicationContext(), "Lembrete Cadastrado com sucesso", Toast.LENGTH_LONG).show();
                            finish();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT);
                        }


                    }
                }
            });

            btnLimpar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    data.setText("");
                    hora.setText("");

                }
            });
        }
    }

    private void binding() {
        data = (EditText) findViewById(R.id.txtData);
        hora = (EditText) findViewById(R.id.txtHora);
        pronto = (CheckBox) findViewById(R.id.chkPronto);
        spnTratamento = (Spinner) findViewById(R.id.spnTratamento);
        btnSalvar = (Button) findViewById(R.id.btnAgendaSalvar);
        btnLimpar = (Button) findViewById(R.id.btnAgendaLimpar);
        util = new ActivityUtil(this,this);
    }

    private void preencheSpinnerTratamento() {
        tratamentos = new TratamentoDao(getApplicationContext()).obterTodos();

        List<String> list = new ArrayList<>();

        for(Tratamento t: tratamentos){
            list.add(t.getPaciente().getNome() + " - " + t.getRemedio().getNome());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTratamento.setAdapter(dataAdapter);
    }

    private void selecionarTratamento(Tratamento t) {

        List<String> list = new ArrayList<>();
        list.add(t.getPaciente().getNome() + " - " + t.getRemedio().getNome());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTratamento.setAdapter(dataAdapter);
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

