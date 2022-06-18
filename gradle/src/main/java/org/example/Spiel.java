package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Spiel implements ISpiel{
    String geheimesWort;  //zu erraten
    final int leben = 10;   //Anzahl leben
    ArrayList<Character> fehlversuche = new ArrayList<Character>();  //Buchstaben aus falschen Rateversuchen
    Character[] erraten;  //erratene Indizes
    Nutzer spieler1;
    Nutzer spieler2;
    int schierigkeitsgrad;
    int[] punkteNachSchwierigkeit = {5, 10, 15, 20};   //maximaler Gewinn, Level = Index+1
    int[] lebenNachSchwierigkeit = {20, 15, 10, 5};    //Leben, Level = Index+1




    public Spiel(Nutzer spieler1, Nutzer spieler2, int schwierigkeitsgrad){
        String wort = this.getRaetsel();
        this.geheimesWort = wort;
        this.erraten = new Character[geheimesWort.length()];

        Arrays.fill(erraten,  "0".charAt(0));  //alle Felder mit "0" ausfüllen
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;

    }

    public String getFehlversuche(){
        return "Buchstaben, die nicht oder nicht mehr zum Wort gehoeren: " + this.fehlversuche;
    }

    public String getErraten(){

        StringBuilder zustand = new StringBuilder("Teillösung: ");
        for(Character c: erraten){   //durch Array iterieren
            if(c.equals("0".charAt(0))){  //ist Index noch nicht erraten? --> 0 ist Inhalt
                zustand.append("_");
            }
            else zustand.append(c);
        }
        return "Bisheriger Zustand Ihrer Lösung: " + zustand;
    }


    public boolean rateVersuch(Character buchstabe, Nutzer spieler) {

        for (int i = 0; i < geheimesWort.length(); i++) {
            if (buchstabe.equals(geheimesWort.charAt(i))) {  //Buchstabe im Wort
                if (!erraten[i].equals(buchstabe)) { //noch nicht bisher erraten
                    return true;  //richtiger Versuch
                }
            }
        }
        fehlversuche.add(buchstabe);
        return false;
    }

    public String getRaetsel(){
        Random random = new Random();
        int zahl = random.nextInt() % Main.woerter.length;  //Zufallszahl aus Intervall von Wörterarray generieren
        zahl = Math.abs(zahl);
        System.out.println(Main.woerter.length);
        System.out.println(zahl);
        return Main.woerter[zahl];   //Wort zurückgeben
    }

    public String toString(){
        return "Spiel: " + geheimesWort + " Spieler 1: " + spieler1 + " Spieler 2: " + spieler2;
    }

}
