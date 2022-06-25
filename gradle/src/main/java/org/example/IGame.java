package org.example;

import java.util.Random;

public interface IGame {
    /**
     * Gibt die Anzahl der Fehlerversuche zurück
     * @return String von Liste mit falschen Buchstaben
     */
    public String getFehlversuche();

    /**
     * Liefert zurück, ob das Raetsel geloest wurde oder nicht
     * @return true = fertig, false = noch nicht alle Buchstaben erraten
     */
    public boolean erraten();


    /**
     * Liefert bisher erratenen Teil des Raetsels zurück
     * @return String der bisherigen Teilloesung mit _ als Lücke für noch nicht erratene Buchstaben
     */
    public String getErraten();


    /**
     * Nutzer kann einen Buchstabenrateversuch durchführen
     * @param buchstabe Buchstabe, den Nutzer testen will
     * @param spieler Nutzer, der gerade raet
     * @return true = richtiger Versuch, false = falscher Versuch
     */
    public boolean rateVersuchChar(Character buchstabe, Nutzer spieler);

    /**
     * Nutzer kann ganzes Wort erraten
     * @param wort Rateversuch als ganzes Wort
     * @param spieler Nutzer, der gerade raet
     * @return true = richtig geraten, false = falsch geraten
     */
    public boolean rateVersuchWort(String wort, Nutzer spieler);


    /**
     * Neues Wort für Raetsel von Spiel zufällig auswaehlen und als "geheimesWort" im Spiel speichern
     * @return neues Raetsel in Form eines Wortes als String
     */
    public String getRaetsel();

    /**
     * Liefert Spiel als String mit Basisinfos zurück
     * @return Spiel mit Schwierigkeit und Mitgliederzahl
     */
    public String toString();

}



