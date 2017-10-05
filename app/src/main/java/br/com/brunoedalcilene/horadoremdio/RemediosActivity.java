package br.com.brunoedalcilene.horadoremdio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.brunoedalcilene.horadoremdio.dao.RemedioDao;
import br.com.brunoedalcilene.horadoremdio.model.Remedio;
import br.com.brunoedalcilene.horadoremdio.util.ActivityUtil;

public class RemediosActivity extends AppCompatActivity {

    ListView lstRemedios;
    List<Remedio> remedios;
    FloatingActionButton fab;
    ActivityUtil util;
    ImageButton pesquisar;
    EditText txtPesquisa;

    private static int CADASTRO_REMEDIOS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        preencherListView(null);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util.chamarActivity(CadastroRemedioActivity.class,CADASTRO_REMEDIOS,null,null);
            }
        });

        lstRemedios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                util.chamarActivity(CadastroRemedioActivity.class,CADASTRO_REMEDIOS,"remedio",remedios.get(i));
            }
        });

        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preencherListView(txtPesquisa.getText().toString());
            }
        });
    }

    private void binding() {

        lstRemedios = (ListView) findViewById(R.id.lstRemedios);
        fab = (FloatingActionButton) findViewById(R.id.btnNovoRemedio);
        util = new ActivityUtil(getApplicationContext(), this);
        pesquisar = (ImageButton) findViewById(R.id.btnRemedioPesquisar);
        txtPesquisa = (EditText) findViewById(R.id.txtRemedioBusca);

    }

    private void preencherListView(String nome) {
        if (nome == null) {
            remedios = new RemedioDao(getApplicationContext()).obterTodos();
        }else{
            remedios = new RemedioDao(getApplicationContext()).obterPorNome(nome);
        }

        if (remedios != null && !remedios.isEmpty()) {

            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
            for (Remedio r : remedios) {

                Map<String, String> datum = new HashMap<String, String>(2);
                datum.put("nome", r.getNome());
                datum.put("descricao", r.getDescricao());
                data.add(datum);
            }

            SimpleAdapter adapter = new SimpleAdapter(this, data,
                    android.R.layout.simple_list_item_2,
                    new String[] {"nome", "descricao"},
                    new int[] {android.R.id.text1,
                            android.R.id.text2});

            lstRemedios.setAdapter(adapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CADASTRO_REMEDIOS){
            preencherListView(null);
        }
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
