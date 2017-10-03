package br.com.brunoedalcilene.horadoremdio;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
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
    ExpandableListView lstTratamentos;
    ActivityUtil util;
    private static int CADASTRO_TRATAMENTOS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tratamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();
//        preencherListView(null);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void binding() {
        lstTratamentos = (ExpandableListView) findViewById(R.id.lstTratamentos);
        util = new ActivityUtil(getApplicationContext(),this);
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
