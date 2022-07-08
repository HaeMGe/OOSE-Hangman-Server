package org.example;

import static org.example.Main.warteraum;

public interface INutzer {
    /**
     * Gibt Nutzername zurück
     * @return Name als String
     */
    public String getName();


    /**
     * Die Leben, die man je nach Level als Spieler besitzt neu setzen.
     * @param anzLeben Anzahl der Leben in Abhängigkeit vom Schwierigkeitsgrad
     */
    public void setLeben(int anzLeben);

    /**
     * Nutzer mit seinem Namen und Punkten als String ausgeben
     * @return Nutzername und Punkte
     */
    public String toString();
}

