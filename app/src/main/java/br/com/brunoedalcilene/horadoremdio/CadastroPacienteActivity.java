package br.com.brunoedalcilene.horadoremdio;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.brunoedalcilene.horadoremdio.dao.PacienteDao;
import br.com.brunoedalcilene.horadoremdio.model.Paciente;
import br.com.brunoedalcilene.horadoremdio.util.ActivityUtil;

public class CadastroPacienteActivity extends AppCompatActivity {

    Toolbar toolbar;
    Paciente paciente;
    ActivityUtil util;
    View content;
    Button btnSalvar, btnLimpar;
    EditText nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_paciente);
        binding();

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        paciente = (Paciente) getIntent().getSerializableExtra("paciente");

        if (paciente != null) {
            getSupportActionBar().setTitle(paciente.getNome());
            nome.setText(paciente.getNome());
            util.bloquearElementos(findViewById(android.R.id.content), false);

        } else {

            btnSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (nome.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "O campo Nome é obrigatório.", Toast.LENGTH_SHORT).show();
                    } else {
                        paciente = new Paciente();
                        paciente.setNome(nome.getText().toString());

                        new PacienteDao(getApplicationContext())
                                .inserir(paciente);
                        Toast.makeText(getApplicationContext(), "Paciente Cadastrado com sucesso", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            });

            btnLimpar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nome.setText("");
                }
            });
        }
    }

    private void binding() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        content = (View) findViewById(android.R.id.content);
        util = new ActivityUtil(getApplicationContext(), this);
        btnSalvar = (Button) findViewById(R.id.btnPacienteSalvar);
        btnLimpar = (Button) findViewById(R.id.btnPacienteLimpar);
        nome = (EditText) findViewById(R.id.txtPacienteNome);
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
