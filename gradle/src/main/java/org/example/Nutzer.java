package org.example;
import java.util.Scanner;

/**
 * Jeder Spieler muss sich mit einem Nutzernamen anmelden. Dieser Name wird hier zur Instanziierung eines
 * neuen Nutzers verwendet, sodass später wieder auf diesen Nutzer zugegriffen werden kann.
 */
public class Nutzer implements INutzer{

    static Scanner sc = new Scanner(System.in);
    String name;  //Name des Nutzers
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
