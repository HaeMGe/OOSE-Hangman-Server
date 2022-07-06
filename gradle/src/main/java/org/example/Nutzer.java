package org.example;
import java.util.Scanner;


public class Nutzer{


    static Scanner sc = new Scanner(System.in);
    String name;
    int leben;  //Leben im aktuellen Spiel
    int strafe;  //Strafen im akutellen Spiel (falsches Wort erraten)

    //parameterloser Konstruktor
    public Nutzer(String name){
        this.name = name;
        Main.nutzerListe.add(this);
    }



    public String getName() {
        return name;
    }


    public void setLeben(int anzLeben){
        leben = anzLeben;
    }
    public String toString(){
        return this.name;
    }
}
