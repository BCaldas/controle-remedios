package br.com.brunoedalcilene.horadoremdio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.brunoedalcilene.horadoremdio.dao.RemedioDao;
import br.com.brunoedalcilene.horadoremdio.model.Remedio;

public class CadastroRemedioActivity extends AppCompatActivity {

    Button btnSalvar, btnLimpar;
    EditText nome, descricao;
    Remedio remedio;
    TextView lblNovoRemedio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_remedio);

        binding();

        remedio = (Remedio) getIntent().getSerializableExtra("remedio");

        if (remedio != null) {
            nome.setText(remedio.getNome());
            descricao.setText(remedio.getDescricao());

            View view = findViewById(android.R.id.content);
            bloquearElementos(view,false);
            lblNovoRemedio.setText(remedio.getNome());
        } else {

//            btnSalvar.setEnabled(false);
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
        lblNovoRemedio = (TextView) findViewById(R.id.lblTituloCadastroRemedio);
    }

    private void chamarTela(Class activity, int requestCode) {

        Intent i = new Intent(getApplicationContext(), activity);
        startActivityForResult(i, requestCode);
    }

    private void bloquearElementos(View root, boolean enabled) {
        // Desabilito a própria View
        root.setEnabled(enabled);

        // Se ele for um ViewGroup, isso é, comporta outras Views.
        if(root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) root;

            // Percorro os filhos e desabilito de forma recursiva
            for(int i = 0; i < group.getChildCount(); ++i) {
                bloquearElementos(group.getChildAt(i), enabled);
            }
        }
    }

    private boolean validarCampos() {
        if (nome.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
