package com.example.juan.realm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import static android.R.attr.y;
import static java.lang.Integer.parseInt;

/**
 * Created by Juan on 11/03/2017.
 */

public class Persona extends RealmObject {



    @PrimaryKey
    private String id;
    @Required
    public String nom;
    @Required
    private String genere;
    @Required
    private String naixement;

    private int edat;

    public Persona(){

    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getGenere() {
        return genere;
    }

    public String getNom() {return nom;}

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDataNaixament() {return naixement;}

    public void setDataNaixament(String dataNaixament) throws ParseException {this.naixement = dataNaixament;}

    public void setAge(String _naixement){
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

       int year =  Integer.parseInt(_naixement.substring(0,4));
        int month = Integer.parseInt(_naixement.substring(5,6));
        int day = Integer.parseInt(_naixement.substring(8,9));

         int y1 = cal.get(Calendar.YEAR);
        int anio = (y1-1) - year;
        System.out.println("AÑO REAL: "+year);
        System.out.println("AÑO ACTUAL: "+y1);

        this.edat = anio;


    }
    public int getAge() throws IllegalAccessException, InstantiationException {return edat;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
