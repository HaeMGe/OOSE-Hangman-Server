package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game implements IGame{
    String geheimesWort;  //zu erraten
    final int leben = 10;   //Anzahl leben
    ArrayList<Character> fehlversuche = new ArrayList<Character>();  //Buchstaben aus falschen Rateversuchen
    ArrayList<String> fehlversucheWort = new ArrayList<String>();  //Wörter aus falschen Rateversuchen
    Character[] erraten;  //erratene Indizes
    ArrayList<Nutzer> members = new ArrayList<>();
    int schierigkeitsgrad;
    int[] punkteNachSchwierigkeit = {5, 10, 15, 20};   //maximaler Gewinn, Level = Index+1
    int[] lebenNachSchwierigkeit = {20, 15, 10, 5};    //Leben, Level = Index+1
    boolean fertig; //Ist Wort erraten oder alle Leben weg? --> Spiel beendet
    int amZugIndex; //Gibt an, ob spieler 0 oder 1 am Zug ist



    public Game(int level) {
        this.schierigkeitsgrad = level;  //Schwierigkeitgrad von Spiel
        this.geheimesWort = this.getRaetsel();   //neues Wort für Spiel
        this.erraten = new Character[geheimesWort.length()];  //erratenes Wort muss richtige Länge haben
        Arrays.fill(erraten,  "0".charAt(0));  //alle Felder mit "0" ausfüllen
        amZugIndex = 0;
    }



    public String getFehlversuche(){
        return this.fehlversuche.toString();
    }

    public boolean erraten(){
        for(int i = 0; i < geheimesWort.length(); i++){
            if(geheimesWort.charAt(i) != (erraten[i]))
                return false;
        }
        return true;
    }

    public String spielStatus(String name){
        Nutzer n = null;
        for(Nutzer p: members){
            if(p.getName().equals(name)){
                n = p;
            }
        }

        int leben = n.leben;

      //  }
        return"'erraten':'"+getErraten()+"','leben':'"+leben+"','spielVorbei':'"+erraten()+"','fehlversuche':'"+getFehlversuche()+"'";
    }

    public boolean istAmZug(String name){
        //Gibt an, ob derNutzer mit name am Zug ist, oder nicht
        if(members.get(amZugIndex).getName().equals(name)){
            return true;
        }else{
            return false;
        }
    }


    public String getErraten(){

        StringBuilder zustand = new StringBuilder("Teillösung: ");
        for(Character c: erraten){   //durch Array iterieren
            if(c.equals("0".charAt(0))){  //ist Index noch nicht erraten? --> 0 ist Inhalt
                zustand.append("_");
            }
            else zustand.append(c);
        }
        System.out.println(zustand);
        return ""+zustand;
    }


    public boolean rateVersuchChar(Character buchstabe, Nutzer spieler) {
        boolean geraten = false;
        for (int i = 0; i < geheimesWort.length(); i++) {
            if (buchstabe.equals(geheimesWort.charAt(i))) {  //Buchstabe im Wort
                if (!erraten[i].equals(buchstabe)) { //noch nicht bisher erraten
                    erraten[i] = buchstabe;  //Buchstabe wurde erraten
                    geraten=  true;  //richtiger Versuch
                }
            }
        }
        if(geraten){
            return geraten;
        }else {
            fehlversuche.add(buchstabe);
            spieler.leben = spieler.leben - 1; //Leben abziehen
            return geraten;
        }
    }

    public boolean rateVersuchWort(String wort, Nutzer spieler) {
        if(geheimesWort.equals(wort))
            return true;
        else {
            spieler.leben = spieler.leben - 2; //Leben abziehen
            spieler.strafe++;  //falsches Wort erraten = Strafe
            this.fehlversucheWort.add(wort); //Wort der Liste mit falschen Versuchen hinzufügem
            return false;
        }
    }


    public String getRaetsel(){
        Random random = new Random();
        int zahl = random.nextInt() % Main.woerter.size();  //Zufallszahl aus Intervall von Wörterarray generieren
        zahl = Math.abs(zahl);
        //System.out.println(Main.woerter.length);
        //System.out.println(zahl);
        System.out.println("Das zu erratende Wort für den Pool ist: "+Main.woerter.get(zahl));
        return Main.woerter.get(zahl);   //Wort zurückgeben
    }

    public String toString(){
        return "Spiel: " + geheimesWort + "Spieler: " + members;
    }

}

