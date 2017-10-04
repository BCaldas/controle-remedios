package br.com.brunoedalcilene.horadoremdio;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.brunoedalcilene.horadoremdio.dao.AgendaDao;
import br.com.brunoedalcilene.horadoremdio.model.Agenda;

public class HistoricoActivity extends AppCompatActivity {

    ListView historico;
    List<Agenda> lembretes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding();
        preencherListView();
    }

    private void binding() {
        historico = (ListView) findViewById(R.id.lstHistorico);
    }

    private void preencherListView() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf.applyPattern("dd/MM/yyyy - HH:mm");

        lembretes = new AgendaDao(getApplicationContext()).obterPorStatus(true);


        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for (Agenda a : lembretes) {

            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("paciente", a.getTratamento().getPaciente().getNome());
            datum.put("detalhes", a.getTratamento().getRemedio().getNome() + " - " + sdf.format(a.getDataHoraConsumo()));
            data.add(datum);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"paciente", "detalhes"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});

        historico.setAdapter(adapter);
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
