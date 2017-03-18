package com.example.juan.realm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        /*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date result =  df.parse(dataNaixament);*/

        this.naixement = dataNaixament;
    }

    /*
    *	@return int Edat de la persona a partir de la data de naixement
    */
    /*public int getAge() throws IllegalAccessException, InstantiationException {
        Date today;
        today = Date.class.newInstance();
        if ((naixement != null) && (today != null)) {
            return today.getYear()-naixement.getYear();
        } else {
            return 0;
        }
    }*/

    /*@Override
    public String toString() {
        return nom + '\t' + genere.toString() + '\t' + getAge();
    }

    public void printPersona(List<Persona> llista, CheckPerson test){
        for(Persona pers : llista){
            if(test.test(pers)){
                System.out.println(pers.toString());
            }
        }


    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
