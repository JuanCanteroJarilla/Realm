package com.example.juan.realm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.example.juan.realm.R.id.lv;

public class EliminarActivity extends AppCompatActivity {
    Realm realm;
    ListView lv;
    PersonaAdapter adapter;
    TextView tx1;
    Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.llistar_listview);

        realm = Realm.getDefaultInstance();

        lv = (ListView)findViewById(R.id.lv);
        sp = (Spinner)findViewById(R.id.spinner);
        sp.setVisibility(View.GONE);

        final RealmResults<Persona> results = realm.where(Persona.class).findAll();
        adapter = new PersonaAdapter(results);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Persona p = results.get(i);
                        p.deleteFromRealm();
                        Toast.makeText(EliminarActivity.this,"Contacte eliminat",Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }
}
