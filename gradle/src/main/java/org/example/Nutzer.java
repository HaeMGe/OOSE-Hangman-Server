package org.example;

import com.google.gson.Gson;

import java.util.ArrayList;

import static org.example.Main.nutzerListe;
import static org.example.Main.warteraum;

public class Nutzer implements INutzer{


    String name;
    String passwort;
    int punkte;  //Punktestand eines Nutzers

    Spiel spielAktuell;

    //parameterloser Konstruktor
    public Nutzer(String name, String passwort){
        this.name = name;
        this.passwort = passwort;
        this.punkte = 0;
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




    public String start(int schwierigkeit){
        String ergebnis = start(5, schwierigkeit);
        return ergebnis;
    }

    private String start(int counter, int schwierigkeit){
        while(Main.warteraum.size()<2){  //nur Nutzer selbst im Warteraum
            if(counter>0) {
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){return "Fehler";}   //Warteschleife
                counter--;  //Zähler runtersetzen --> insgesamt 5-mal alle 1000 msek abfragen, ob Partner da
            }
            else {
                return "Leider noch kein Gegner gefunden :-(";
            }
        }
        if(Main.warteraum.get(0) == this){    //ist erste oder zweite Person der Nutzer im Warteraum?
            Spiel neuesSpiel = new Spiel(this , warteraum.get(1), schwierigkeit);
            this.spielAktuell = neuesSpiel;  //Nutzer das Spiel zuweisen
            warteraum.get(1).spielAktuell = neuesSpiel;  //Gegner das Spiel zuweisen
            warteraum.remove(this);   //Spieler aus Warteraum entfernen
            warteraum.remove(warteraum.get(1));
            return "Dein Gegner: " + warteraum.get(1);   //Gegner zurückliefern
        }
        else {
            Spiel neuesSpiel = new Spiel(this, warteraum.get(0), schwierigkeit);
            this.spielAktuell = neuesSpiel;
            warteraum.get(0).spielAktuell = neuesSpiel;
            warteraum.remove(this);   //Spieler aus Warteraum entfernen
            warteraum.remove(warteraum.get(0));
            return "Dein Gegner: " + warteraum.get(0);
        }
    }

    public static void startAnmeldungWarteraum(String name, int schwierigkeit){
        for(Nutzer nutzer: Main.nutzerListe){
            if(nutzer.getName().equals(name))
                Main.warteraum.add(nutzer);  //in den Warteraum kommen
            nutzer.start(schwierigkeit);
        }
    }


    public String toString(){
        return this.name + " Punkte: " + this.punkte;
    }
}
