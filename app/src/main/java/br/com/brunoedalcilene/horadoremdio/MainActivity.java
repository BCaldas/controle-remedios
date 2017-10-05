package br.com.brunoedalcilene.horadoremdio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.brunoedalcilene.horadoremdio.dao.AgendaDao;
import br.com.brunoedalcilene.horadoremdio.model.Agenda;
import br.com.brunoedalcilene.horadoremdio.model.Paciente;
import br.com.brunoedalcilene.horadoremdio.model.Tratamento;
import br.com.brunoedalcilene.horadoremdio.util.ActivityUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView lstAgenda;
    List<Agenda> lembretes;
    EditText txtPesquisa;
    ImageButton pesquisar;

    private MainActivity activity;
    private ActivityUtil util;
    private static int REMEDIOS = 1;
    private static int PACIENTES = 2;
    private static int TRATAMENTOS = 3;
    private static int CADASTRO_AGENDA = 4;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding();

        preencherListView(null);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util.chamarActivity(CadastroAgendaActivity.class, CADASTRO_AGENDA,null,null);
            }
        });

        lstAgenda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                util.chamarActivity(CadastroAgendaActivity.class, CADASTRO_AGENDA,"agenda",lembretes.get(i));
            }
        });

        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preencherListView(txtPesquisa.getText().toString());
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_agenda) {


        } else if (id == R.id.nav_tratamentos) {
            util.chamarActivity(TratamentoActivity.class,TRATAMENTOS,null,null);

        } else if (id == R.id.nav_remedios) {
            util.chamarActivity(RemediosActivity.class,REMEDIOS,null,null);

        } else if (id == R.id.nav_pacientes) {
            util.chamarActivity(PacientesActivity.class,PACIENTES,null,null);

        } else if (id == R.id.nav_historico){
            util.chamarActivity(HistoricoActivity.class,0,null,null);
            
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void binding() {
        activity = (MainActivity) this;
        util = new ActivityUtil(getApplicationContext(),this);
        lstAgenda = (ListView) findViewById(R.id.lstAgenda);
        pesquisar = (ImageButton) findViewById(R.id.btnAgendaPesquisar);
        txtPesquisa = (EditText) findViewById(R.id.txtAgendaBusca);
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Clique em voltar novamente para sair", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            preencherListView(null);
    }

    private void preencherListView(String nome) {

        if (nome == null) {
            lembretes = new AgendaDao(getApplicationContext()).obterPorStatus(false);
        } else {
            lembretes = new AgendaDao(getApplicationContext()).obterPorNomeEStatus(nome,false);
        }

        if (lembretes != null && !lembretes.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf.applyPattern("dd/MM/yyyy - HH:mm");

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

            lstAgenda.setAdapter(adapter);
        }
    }
}
