package br.com.brunoedalcilene.horadoremdio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.brunoedalcilene.horadoremdio.dao.TratamentoDao;
import br.com.brunoedalcilene.horadoremdio.model.Tratamento;
import br.com.brunoedalcilene.horadoremdio.util.ActivityUtil;

public class TratamentoActivity extends AppCompatActivity {

    List<Tratamento> tratamentos;
    ListView lstTratamentos;
    ActivityUtil util;
    ImageButton pesquisar;
    EditText txtPesquisa;
    private static int CADASTRO_TRATAMENTOS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tratamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();
        preencherListView(null);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util.chamarActivity(CadastroTratamentoActivity.class,CADASTRO_TRATAMENTOS,null,null);
            }
        });

        lstTratamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                util.chamarActivity(CadastroTratamentoActivity.class,CADASTRO_TRATAMENTOS,"tratamento",tratamentos.get(i));
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
        lstTratamentos = (ListView) findViewById(R.id.lstTratamentos);
        util = new ActivityUtil(getApplicationContext(),this);
        pesquisar = (ImageButton) findViewById(R.id.btnTratamentoPesquisar);
        txtPesquisa = (EditText) findViewById(R.id.txtTratamentoBusca);
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
            tratamentos = new TratamentoDao(getApplicationContext()).obterTodos();
        }else{
            tratamentos = new TratamentoDao(getApplicationContext()).obterPorNome(nome);
        }

        if (tratamentos != null && !tratamentos.isEmpty()) {
            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
            for (Tratamento t : tratamentos) {

                Map<String, String> datum = new HashMap<String, String>(2);
                datum.put("paciente", t.getPaciente().getNome());
                datum.put("remedio", t.getRemedio().getNome());
                data.add(datum);
            }

            SimpleAdapter adapter = new SimpleAdapter(this, data,
                    android.R.layout.simple_list_item_2,
                    new String[] {"paciente", "remedio"},
                    new int[] {android.R.id.text1,
                            android.R.id.text2});

            lstTratamentos.setAdapter(adapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CADASTRO_TRATAMENTOS){
            preencherListView(null);
        }
    }
}
