package org.example;

import java.util.ArrayList;

public class Spiel {
    String geheimesWort;  //zu erraten
    final int leben = 10;   //Anzahl leben
    ArrayList<Character> fehlversuche = new ArrayList<Character>();
    Character[] erraten;



    public Spiel(String wort){
        this.geheimesWort = wort;
        this.erraten = new Character[geheimesWort.length()];
    }

    public boolean rateVersuch(Character buchstabe) {
        for (int i = 0; i < this.geheimesWort.length(); i++) {
            if (buchstabe.equals(geheimesWort.charAt(i))) {  //Buchstabe im Wort
                if (!erraten[i].equals(buchstabe)) { //noch nicht bisher erraten
                    return true;  //richtiger Versuch
                }
            }
        }
        fehlversuche.add(buchstabe);
        return false;
    }

}
