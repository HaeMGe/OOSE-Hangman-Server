package org.example;

import com.google.gson.Gson;

import java.util.ArrayList;

import static org.example.Main.nutzerListe;
import static org.example.Main.warteraum;

public class Nutzer{


    String name;
    String passwort;
    int punkte;  //Punktestand eines Nutzers

    //parameterloser Konstruktor
    public Nutzer(String name, String passwort){
        this.name = name;
        this.passwort = passwort;
        Main.nutzerListe.add(this);
    }



    public String getName() {
        return name;
    }
    public String getPasswort() {
        return passwort;
    }
    public int getPunktestand() {
        return punkte;
    }
    public void setPunktestand(int neuerWert){
        this.punkte = neuerWert;
    }




    public String start(){
        while(Main.warteraum.size()<2){  //nur Nutzer selbst im Warteraum
            start();   //Warteschleife
        }
        if(Main.warteraum.get(0) == this){    //ist erste oder zweite Person der Nutzer im Warteraum?
            return "Gegner: " + warteraum.get(1);   //Gegner zurückliefern
        }
        else return "Gegner: " + warteraum.get(0);
    }


    public static void startAnmeldung(String name){
        for(Nutzer nutzer: Main.nutzerListe){
            if(nutzer.getName().equals(name))
                Main.warteraum.add(nutzer);  //in den Warteraum kommen
            nutzer.start();
        }
    }


    public String toString(){
        return "Spieler: " + this.name + "Punkte: " + this.punkte;
    }
}
