package com.example.juan.realm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.UUID;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Button add, view, update, delete;
    EditText  name;
    TextView text;
    Realm realm;
    EditText nomOutput,naixOutput;
    RadioButton home,dona;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        add = (Button) findViewById(R.id.add);
        text = (TextView) findViewById(R.id.text);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Afegir) {
            LayoutInflater li = LayoutInflater.from(MainActivity.this);
            View dialogView = li.inflate(R.layout.custom_dialog, null);

            final EditText nom = (EditText) dialogView
                    .findViewById(R.id.etAfegir);
            final EditText dNaixement = (EditText) dialogView
                    .findViewById(R.id.etNaixement);

            final RadioButton home = (RadioButton)dialogView.findViewById(R.id.rbHome);
            final RadioButton dona = (RadioButton)dialogView.findViewById(R.id.rbDona);

            //SE CREA UN ALERTDIALOG PARA EL INPUT DEL USUARIO
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Introdueix una nova persona"); //Set Alert dialog title here
            alert.setView(dialogView);

            alert
                    .setCancelable(false)
                    //SI SE PULSA OK, SE REALIZA LA TRANSACCIÓN REALM
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    realm.beginTransaction();
                                    //COMO "NOM" ES LA PK, SE LE PASA COMO 2º ARGUMENTO EN CREATEOBJECT
                                    Persona persona = realm.createObject(Persona.class, UUID.randomUUID().toString());
                                    //persona.setId(id);
                                        persona.setNom(nom.getText().toString());
                                    try {
                                        persona.setDataNaixament(dNaixement.getText().toString());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    if(home.isChecked()){
                                        persona.setGenere("Home");
                                    }
                                    if(dona.isChecked()){
                                        persona.setGenere("Dona");
                                    }

                                    realm.commitTransaction();

                                    Toast.makeText(MainActivity.this,"Contacte afegit",Toast.LENGTH_LONG).show();
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

        } else if (id == R.id.Llistar) {
            Intent intent = new Intent(this, LlistarActivity.class);
            startActivity(intent);

        } else if (id == R.id.Modificar) {
            Intent intent = new Intent(this, ModificarActivity.class);
            startActivity(intent);

        } else if (id == R.id.Eliminar) {
            Intent intent = new Intent(this, EliminarActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}
