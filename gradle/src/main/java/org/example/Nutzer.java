package org.example;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Scanner;

import static org.example.Main.nutzerListe;
import static org.example.Main.warteraum;

public class Nutzer{


    static Scanner sc = new Scanner(System.in);
    String name;
    int punkte;  //Punktestand eines Nutzers

    Spiel spielAktuell;
    int leben;  //Leben im aktuellen Spiel
    int strafe;  //Strafen im akutellen Spiel (falsches Wort erraten)

    //parameterloser Konstruktor
    public Nutzer(String name){
        this.name = name;
        this.punkte = 0;
        Main.nutzerListe.add(this);
    }



    public String getName() {
        return name;
    }

    public int getPunktestand() {
        return punkte;
    }
    public void setPunktestand(int neuerWert){
        this.punkte = neuerWert;
    }




    public String start(int schwierigkeit){
        String ergebnis = start(1, schwierigkeit);
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
            Nutzer gegner = warteraum.get(1);
            warteraum.remove(warteraum.get(1));
            return "Dein Gegner: " + gegner;   //Gegner zurückliefern
        }
        else {
            Spiel neuesSpiel = new Spiel(this, warteraum.get(0), schwierigkeit);
            this.spielAktuell = neuesSpiel;
            warteraum.get(0).spielAktuell = neuesSpiel;
            Nutzer gegner = warteraum.get(0);
            warteraum.remove(this);   //Spieler aus Warteraum entfernen
            warteraum.remove(warteraum.get(0));
            return "Dein Gegner: " + gegner;
        }
    }

    public static void startAnmeldungWarteraum(String name, int schwierigkeit){
        for(Nutzer nutzer: Main.nutzerListe){
            if(nutzer.getName().equals(name))
                Main.warteraum.add(nutzer);  //in den Warteraum kommen
            nutzer.start(schwierigkeit);
        }
    }

    public String rateVersuch(int option){
        if(option == 0)  //Buchstabe raten
            return buchstabeRaten();
        else{
            if(option == 1){  //Wort raten
                return wortRaten();
            }
        }
        return null;
    }
    public String buchstabeRaten(){ //wird von Client an Server gesendet
        System.out.println("Ihr Buchstabenversuch:");
        String c = sc.next();
        return c;
    }
    public String wortRaten(){
        System.out.println("Ihr Wortversuch:");
        return sc.next();
    }

    public String toString(){
        return this.name + " Punkte: " + this.punkte;
    }
}
