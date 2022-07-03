package org.example;

import java.util.ArrayList;

public class Pool {
    Game spiel; //zugehoeriges Spiel
    //ArrayList<Nutzer> mitglieder = new ArrayList<>();  //1 oder 2 Spieler
    int level;  //Schwierigkeitsgrad: 1, 2, 3, 4

    int id;  //selbst gewählter Integer

    //Nutzer eröffnet Pool unter Angabe von Schwierigkeit
    public Pool(Nutzer initiator, int level, int id){
        this.id = id;
        this.level = level;
       this.spiel = new Game(level);  //Neues Spiel mit richtigem Schwierigkeitsgrad erschaffen
        Main.poolListe.add(this); //Zur Poolliste hinzufügen
        spiel.members.add(initiator);
        initiator.leben = 10;
    }


    public int anzahlSpieler(){
        return spiel.members.size();
    }



    public String toString(){
        return "ID: " + id + " Nummer in Liste: " + Main.poolListe.indexOf(this) + ", Schwierigkeit: " + level + " Mitgliederzahl: " + anzahlSpieler();
    }
}
