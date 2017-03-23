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

/**
 * Created by Juan on 11/03/2017.
 */

public class Persona extends RealmObject {



    @PrimaryKey
    private String id;

    public String nom;

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    private String genere;

    //private Genere genere;

    @Required
    private String naixement;

    private int edat;


    /*public boolean test(Persona p) throws InstantiationException, IllegalAccessException {

        return p.genere == Persona.Genere.HOME && p.getAge() >=18 && p.getAge() <=25;
    }*/

    public Persona(){

    }

    /*public Persona(String nom, Genere genere, String dataNaixament) {
        this.nom = nom;
        this.genere = genere;
        this.dataNaixament = dataNaixament;
    }*/
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    /*public Genere getGenere() {
        return genere;
    }*/
    /*public void setGenere(Genere genere) {
        this.genere = genere;
    }*/
    public String getDataNaixament() {
        return naixement;
    }
    public void setDataNaixament(String dataNaixament) throws ParseException {

        /*SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
        Date result =  df.parse(dataNaixament);
        df.format(result);*/

        this.naixement = dataNaixament;
    }

    /*
    *	@return int Edat de la persona a partir de la data de naixement
    */

    public void setAge(int year,int month,int day){
        final Calendar birthDay = Calendar.getInstance();
        birthDay.set(year, month, day);
        final Calendar current = Calendar.getInstance();
        if (current.getTimeInMillis() < birthDay.getTimeInMillis())
            throw new IllegalArgumentException("age < 0");
        int age = current.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        if (birthDay.get(Calendar.MONTH) > current.get(Calendar.MONTH) ||
                (birthDay.get(Calendar.MONTH) == current.get(Calendar.MONTH) &&
                        birthDay.get(Calendar.DATE) > current.get(Calendar.DATE)))
            age--;
        this.edat= age;
    }
    /*public int getAge() throws IllegalAccessException, InstantiationException {
        Date today;
        today = Date.class.newInstance();
        if ((naixement != null) && (today != null)) {
            return today.getYear()-naixement.getYear();
        } else {
            return 0;
        }
    }*/


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
