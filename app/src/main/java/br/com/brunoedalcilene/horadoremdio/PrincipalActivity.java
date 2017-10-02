package br.com.brunoedalcilene.horadoremdio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.brunoedalcilene.horadoremdio.util.ActivityUtil;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        new ActivityUtil(getApplicationContext(),this)
                .chamarActivity(MainActivity.class,0,null,null);
    }

    @Override
    protected void onResume() {
        finish();
        super.onResume();
    }
}
