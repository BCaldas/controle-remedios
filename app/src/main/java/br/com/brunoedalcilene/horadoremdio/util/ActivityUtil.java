package br.com.brunoedalcilene.horadoremdio.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * Created by bruno on 01/10/2017.
 */

public class ActivityUtil {

    private Context context;
    private FragmentActivity fa;

    public ActivityUtil(Context context, FragmentActivity fa) {
        this.context = context;
        this.fa = fa;
    }

    public void chamarActivity(Class activity, int requestCode, String extra, Serializable objeto) {

        Intent i = new Intent(context, activity);
        if (extra != null && objeto != null) {
            i.putExtra(extra,objeto);
        }
        fa.startActivityForResult(i, requestCode);
    }

    public void bloquearElementos(View root, boolean enabled) {
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
}
