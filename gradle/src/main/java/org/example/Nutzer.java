package org.example;
import java.util.Scanner;


public class Nutzer{


    static Scanner sc = new Scanner(System.in);
    String name;
    int punkte;  //Punktestand eines Nutzers

    Game spielAktuell;
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

    public void setLeben(int anzLeben){
        leben = anzLeben;
    }
    public String toString(){
        return this.name + " Punkte: " + this.punkte;
    }
}
