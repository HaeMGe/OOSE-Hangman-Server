package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Spiel {
    String geheimesWort1;  //zu erraten für Spieler 1
    String geheimesWort2;  //zu erraten für Spieler 2
    final int leben = 10;   //Anzahl leben
    ArrayList<Character> fehlversuche = new ArrayList<Character>();  //Buchstaben aus falschen Rateversuchen
    Character[] erratenS1;  //erratene Indizes Spieler 1
    Character[] erratenS2;  //erratene Indizes Spieler 2

    Nutzer spieler1;
    Nutzer spieler2;



    public Spiel(String wortFuer1, String wortFuer2, Nutzer spieler1, Nutzer spieler2){
        this.geheimesWort1 = wortFuer1;
        this.geheimesWort2 = wortFuer2;

        this.erratenS1 = new Character[geheimesWort1.length()];
        this.erratenS2 = new Character[geheimesWort2.length()];
        Arrays.fill(erratenS1,  "0".charAt(0));  //alle Felder mit "0" ausfüllen
        Arrays.fill(erratenS2,  "0".charAt(0));  //alle Felder mit "0" ausfüllen
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;

    }

    public String getFehlversuche(){
        return "Buchstaben, die nicht oder nicht mehr zum Wort gehoeren: " + this.fehlversuche;
    }

    public String getErraten(int i){
        Character[] erraten;
       if(i == 1) erraten = erratenS1;
       else erraten = erratenS2;

        StringBuilder zustand = new StringBuilder("Teillösung: ");
        for(Character c: erraten){   //durch Array iterieren
            if(c.equals("0".charAt(0))){  //ist Index noch nicht erraten? --> 0 ist Inhalt
                zustand.append("_");
            }
            else zustand.append(c);
        }
        return "Bisheriger Zustand Ihrer Lösung: " + zustand;
    }


    public boolean rateVersuch(Character buchstabe, int spielerNummer) {
        String geheimesWort;
        Character[] erraten;

        if(spielerNummer == 1) {
            geheimesWort = geheimesWort1;
            erraten = erratenS1;
        }
        else {
            geheimesWort = geheimesWort2;
            erraten = erratenS2;
        }


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

    public String toString(int SpielerNummer){
        if(SpielerNummer == 1) {
            return "Spiel: " + geheimesWort1 + " Spieler 1: " + spieler1 + " Spieler 2: " + spieler2;
        }
        else return "Spiel: " + geheimesWort2 + " Spieler 1: " + spieler1 + " Spieler 2: " + spieler2;
    }

}
