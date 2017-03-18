package com.example.juan.realm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.R.attr.id;
import static com.example.juan.realm.R.id.spinner;

public class LlistarActivity extends AppCompatActivity{
    Realm realm;
    TextView text;
    ImageButton bt1,bt2;
    PersonaAdapter adapter;
    ListView lv;
    List<String> llista;
   TextView tx1,tx2;
    //Spinner sp;
    private final String TAG= "TAG";
    private boolean userIsInteracting = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.llistar_listview);
        text = (TextView) findViewById(R.id.text);
        tx1 = (TextView)findViewById(R.id.txNom);
        tx2 = (TextView)findViewById(R.id.txNaixement);

        realm = Realm.getDefaultInstance();

        lv = (ListView)findViewById(R.id.lv);


        final RealmResults<Persona> results = realm.where(Persona.class).findAll();
        adapter = new PersonaAdapter(results);
        lv.setAdapter(adapter);

        //AQUÍ LE AÑADÍ UN SPINNER A LA ACTIVITY, PERO NO ME GUSTA COMO QUEDA

        /*sp = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner,android.R.layout.simple_spinner_item);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(spAdapter);
        sp.setEnabled(true);
        sp.setPrompt("Filtrar dades");

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userIsInteracting = true;
                switch (i){
                    case 1:
                        RealmResults<Persona> results = realm.where(Persona.class).contains("genere","Home").findAll();
                        adapter = new PersonaAdapter(results);
                        lv.setAdapter(adapter);
                        break;
                    case 2:
                        RealmResults<Persona> result2 = realm.where(Persona.class).contains("genere","Dona").findAll();
                        adapter = new PersonaAdapter(result2);
                        lv.setAdapter(adapter);
                        break;
                    case 3:
                        RealmResults<Persona>result3 = realm.where(Persona.class).findAllSorted("nom");
                        adapter = new PersonaAdapter(result3);
                        lv.setAdapter(adapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
}

    //AQUÍ LE AÑADO UN MENU DE OPCIONES (3 PUNTOS) A LA ACTIVITY, PARA FILTRAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                RealmResults<Persona> results = realm.where(Persona.class).contains("genere","Home").findAll();
                adapter = new PersonaAdapter(results);
                lv.setAdapter(adapter);
                return true;
            case R.id.action_settings2:
                RealmResults<Persona> result2 = realm.where(Persona.class).contains("genere","Dona").findAll();
                adapter = new PersonaAdapter(result2);
                lv.setAdapter(adapter);
                return true;
            case R.id.action_settings3:
                RealmResults<Persona>result3 = realm.where(Persona.class).findAllSorted("nom");
                adapter = new PersonaAdapter(result3);
                lv.setAdapter(adapter);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }











}

