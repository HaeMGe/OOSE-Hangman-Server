package org.example;

import static org.example.Main.warteraum;

public interface INutzer {
    /**
     * Gibt Nutzername zurück
     * @return Name als String
     */
    public String getName();
    /**
     * Gibt Nutzerpasswort zurück
     * @return Passwort als String
     */
    public String getPasswort();
    /**
     * Gibt Punktescore zurück
     * @return erspielte Punkte insgesamt
     */
    public int getPunktestand();

    /**
     * Neuen Punktestand aktualisieren
     * @param neuerWert neuer Punktestand
     */
    public void setPunktestand(int neuerWert);


    /**
     * Ein Spiel beginnen, wenn Gegner gefunden
     * @return String, der für die Client-Server-Kommunikation wichtig ist
     */
    public String start(int schwierigkeit);

    /**
     * Interne Implementierung von start()
     * @param counter Zähler, wie lange auf Mitspieler gewartet werden soll
     * @return Meldung, ob Spielpartner gefunden wurde
     */
    private String start(int counter, int schwierigkeit){return null;}


    /**
     * Nutzer mit seinem Namen und Punkten als String ausgeben
     * @return Nutzername und Punkte
     */
    public String toString();
}

