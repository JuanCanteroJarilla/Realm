package com.example.juan.realm;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.R.attr.name;
import static com.example.juan.realm.R.id.lv;
import static com.example.juan.realm.R.id.parent;

public class ModificarActivity extends AppCompatActivity {
    TextView tx1;
    Realm realm;
    ListView lv;
    PersonaAdapter adapter;
    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.llistar_listview);



        realm = Realm.getDefaultInstance();

        lv = (ListView)findViewById(R.id.lv);
        sp = (Spinner)findViewById(R.id.spinner);
        sp.setVisibility(View.GONE);

        final LayoutInflater li = LayoutInflater.from(ModificarActivity.this);
        final View dialogView = li.inflate(R.layout.custom_dialog,null);

        final EditText nom = (EditText) dialogView.findViewById(R.id.etAfegir);
        //final EditText dNaixement = (EditText) dialogView.findViewById(R.id.etNaixement);
        final RadioButton home = (RadioButton)dialogView.findViewById(R.id.rbHome);
        final RadioButton dona = (RadioButton)dialogView.findViewById(R.id.rbDona);

        final RealmResults<Persona> results = realm.where(Persona.class).findAll();
        adapter = new PersonaAdapter(results);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int i, final long parent) {

                final Persona p = results.get(i);
                AlertDialog.Builder alert = new AlertDialog.Builder(ModificarActivity.this);
                alert.setTitle("Modifica les dades"); //Set Alert dialog title here
                alert.setView(dialogView);
                alert
                        .setCancelable(false)
                        //SI SE PULSA OK, SE REALIZA LA TRANSACCIÓN REALM
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        realm.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                p.setNom(nom.getText().toString());
                                                /*try {
                                                    p.setDataNaixament(dNaixement.getText().toString());
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }*/
                                                if(home.isChecked()){
                                                    p.setGenere("Home");
                                                }
                                                if(dona.isChecked()){
                                                    p.setGenere("Dona");
                                                }
                                                adapter.notifyDataSetChanged();


                                            }
                                        });





                                        Toast.makeText(ModificarActivity.this,"Contacte modificat",Toast.LENGTH_LONG).show();
                                    }
                                })
                        //SE SALE DEL ALERTDIALOG
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

    }
}
