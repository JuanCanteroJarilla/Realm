package com.example.juan.realm;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button add, view, update, delete;
    EditText  name;
    TextView text;
    Realm realm;
    EditText nomOutput,naixOutput;
    RadioButton home,dona;
    ArrayList<String> llistadates = new ArrayList<>();

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

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("personas.realm")
                .schemaVersion(0)
                .migration(new MigrationPersona())
                .build();
        Realm.setDefaultConfiguration(config);
        /*RealmConfiguration config = new RealmConfiguration.Builder()
                .name("personas.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);*/

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
            final View dialogView = li.inflate(R.layout.custom_dialog, null);
            final EditText nom = (EditText) dialogView
                    .findViewById(R.id.etAfegir);

            final RadioButton home = (RadioButton)dialogView.findViewById(R.id.rbHome);
            final RadioButton dona = (RadioButton)dialogView.findViewById(R.id.rbDona);
            final ImageButton cal = (ImageButton)dialogView.findViewById(R.id.imCal);
            final Calendar myCalendar = Calendar.getInstance();

            //SE CREA UN ALERTDIALOG PARA EL INPUT DEL USUARIO
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Introdueix una nova persona"); //Set Alert dialog title here
            alert.setView(dialogView);
            Calendar c = Calendar.getInstance();
            final int startYear = c.get(Calendar.YEAR);
            final int startMonth = c.get(Calendar.MONTH);
            final int startDay = c.get(Calendar.DAY_OF_MONTH);

            final DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                int year,month,day;
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

                    year = arg1;
                    month = arg2;
                    day = arg3;
                    month +=1;
                    llistadates.add(year+"-"+month+"-"+day);
                }

            };
            cal.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    DatePickerDialog dp = new DatePickerDialog(MainActivity.this, myDateListener,startYear,startMonth,startDay);
                    dp.show();

                }
            });

            alert
                    .setCancelable(false)
                    //SI SE PULSA OK, SE REALIZA LA TRANSACCIÓN REALM
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {

                               public void onClick(DialogInterface dialog, int id) {
                                   llistadates.remove(0);
                                    realm.beginTransaction();
                                    //COMO "NOM" ES LA PK, SE LE PASA COMO 2º ARGUMENTO EN CREATEOBJECT
                                    final Persona persona = realm.createObject(Persona.class, UUID.randomUUID().toString());
                                        persona.setNom(nom.getText().toString());

                                    try {
                                        persona.setDataNaixament(llistadates.get(0));
                                        persona.setAge(llistadates.get(0));

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
                                   llistadates.remove(0);

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
