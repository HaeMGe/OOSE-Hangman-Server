package org.example;

import java.util.Arrays;
import java.util.Random;

public interface ISpiel {


    /**
     * Gibt falsch erratene Buchstaben zurück
     * @return falsche Buchstaben
     */
    public String getFehlversuche();

    /**
     * Gibt bisherige Teillösung zurück
     * @return bisher korrekte Teillösung von Raetsel
     */
    public String getErraten();


    /**
     * Spieler gibt Buchstaben als Rateversuch ein
     * @param buchstabe Buchstabe wird von Nutzer als Tipp abgegeben
     * @param spieler Spieler, der gerade raet
     * @return true = Buchstabe war richtig, false = Buchstabe war falsch
     */
    public boolean rateVersuchChar(Character buchstabe, Nutzer spieler);
    /**
     * Spieler gibt Wort als Rateversuch ein
     * @param wort Wort wird von Nutzer als Tipp abgegeben
     * @param spieler Spieler, der gerade raet
     * @return true = Wort war richtig, false = Wort war falsch
     */
    public boolean rateVersuchWort(String wort, Nutzer spieler);


    /**
     * Neues Wort für Spiel zufällig auswaehlen
     * @return zu erratendes Wort
     */

    public String getRaetsel();

    /**
     * Spiel als String mit Spieler und Wort zurückgeben.
     */
    public String toString();
}
