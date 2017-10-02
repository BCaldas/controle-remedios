package br.com.brunoedalcilene.horadoremdio;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.brunoedalcilene.horadoremdio.dao.RemedioDao;
import br.com.brunoedalcilene.horadoremdio.model.Remedio;
import br.com.brunoedalcilene.horadoremdio.util.ActivityUtil;

public class CadastroRemedioActivity extends AppCompatActivity {

    Button btnSalvar, btnLimpar;
    EditText nome, descricao;
    Remedio remedio;
    ActivityUtil util;
    View content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_remedio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        remedio = (Remedio) getIntent().getSerializableExtra("remedio");

        if (remedio != null) {
            getSupportActionBar().setTitle(remedio.getNome());

            descricao.setText(remedio.getDescricao());
            util.bloquearElementos(findViewById(android.R.id.content),false);

        } else {

            btnSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (nome.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(),"O campo Nome é obrigatório.",Toast.LENGTH_SHORT).show();
                    } else {
                        remedio = new Remedio();
                        remedio.setNome(nome.getText().toString());
                        remedio.setDescricao(descricao.getText().toString());

                        new RemedioDao(getApplicationContext())
                                .inserir(remedio);
                        Toast.makeText(getApplicationContext(), "Remédio Cadastrado com sucesso", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            });

            btnLimpar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nome.setText("");
                    descricao.setText("");
                }
            });
        }
    }

    private void binding() {
        btnSalvar = (Button) findViewById(R.id.btnRemedioSalvar);
        btnLimpar = (Button) findViewById(R.id.btnRemedioLimpar);
        nome = (EditText) findViewById(R.id.txtRemedioNome);
        descricao = (EditText) findViewById(R.id.txtRemedioDescricao);
        content = (View) findViewById(android.R.id.content);
        util = new ActivityUtil(getApplicationContext(), this);
    }

    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                finish();
                break;
            default:break;
        }
        return true;
    }
}
