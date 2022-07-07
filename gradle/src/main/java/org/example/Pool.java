package org.example;

/**
 * Jedes Spiel wird in der Umgebung eines Pools durchgeführt, welcher hier implementiert ist. Ein Pool hält die Informationen zu dem gespielten
 * Spiel, dem gewählten Schwierigkeitsgrad und der Pool-ID.
 */
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
        initiator.leben = spiel.leben;
        spiel.members.add(initiator);   //Initiator ist sofort Mitglied im Pool
    }

    /**
     * Immer 1 oder 2, bei 2 kann ein Spiel gestartet werden.
     * @return Anzahl der Spieler
     */
    public int anzahlSpieler(){
        return spiel.members.size();
    }


    /**
     * Liefert den Pool als String mit Informationen über seine ID, Schwierigkeitsgrad und Mitgliederanzahl.
     * @return Poolinfos als String
     */
    public String toString(){
        return "ID: " + id + " Nummer in Liste: " + Main.poolListe.indexOf(this) + ", Schwierigkeit: " + level + " Mitgliederzahl: " + anzahlSpieler();
    }
}
