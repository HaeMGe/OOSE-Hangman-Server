package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Hier wird die Logik des Servers getestet, ohne hierbei auf die Restful-Anwendung einzugehen. Die getesteten Klassen sind
 * die Pool-Klasse, die Game-Klasse und die Nutzer-Klasse.
 */

class MainTest {
    Nutzer testUser = new Nutzer("test");   //Testnutzer anlegen
    static String[] args;  //Argumente für die Main
    // Zunächst muss der Server gestartet werden
    @BeforeAll
    public static void startServer() throws IOException {
        Main.main(args);
    }

    //Hier wird ein neuer Pool und ein neuer Nutzer angelegt und überprüft, ob diese Operationen erfolgreich waren.
    @Test
   void neuerPool(){
        Pool pool = new Pool(testUser, 2, 123);  //neuen Pool anlegen
        assertEquals(2,pool.spiel.schierigkeitsgrad);  //wurde der Schwierigkeitsgrad angepasst und ein passendes Spiel angelegt?
        assertEquals(1, pool.anzahlSpieler());         //ist bisher nur der Initiator im Pool?
        Nutzer neuerNutzer = new Nutzer("neuer");
        pool.spiel.members.add(neuerNutzer);
        assertEquals(2, pool.anzahlSpieler());  //Wurde die Anzahl der Spieler aktualisiert?
    }

    //Hier wird ein neuer Nutzer angelegt und überprüft, ob verschiedene Daten und Operationen korrekt sind
    @Test
    void neuerNutzer(){
        Nutzer nutzerNeu = new Nutzer("gamer123");
        assertEquals("gamer123",nutzerNeu.getName()); //Wurde der Name richtig hinterlegt?
        assertTrue(Main.nutzerListe.contains(nutzerNeu));
        assertEquals(0, nutzerNeu.leben);
    }




    //Ein neues Spiel wird angelegt und Operationen auf diesem Spiel werden getestet.
    @Test
    void neuesSpiel(){
        Game spielNeu = new Game(3);  //eigentlich 4, aber Korrektur erst in der Schnittstelle
        assertEquals(3, spielNeu.schierigkeitsgrad);  //richtiges Level?
        assertEquals(5, spielNeu.leben);  //richtige Lebenzahl?
        assertEquals(spielNeu.geheimesWort.length(), spielNeu.erraten.length);  //stimmt die Länge des erratenen String und des geheimen Wortes überein?
    }
    //Hier wird das Raten von richtigen und falschen Buchstaben getestet
    @Test
    void richtigenUndFalschenBuchstabenRaten(){
        Pool neuerPool = new Pool(testUser, 2, 12);  //neuen Pool anlegen
        neuerPool.spiel.geheimesWort = "testWort";
        assertTrue(neuerPool.spiel.rateVersuchChar('e', testUser));  //Buchstabe drin
        assertFalse(neuerPool.spiel.rateVersuchChar('l', testUser)); //Buchstabe nicht drin
    }

    //Hier wird das Raten von richtigen und falschen Wörtern getestet.
    @Test
    void richtigesUndFalschesWortRaten(){
        Pool neuerPool = new Pool(testUser, 2, 12);  //neuen Pool anlegen
        neuerPool.spiel.geheimesWort = "tee";
        assertTrue(neuerPool.spiel.rateVersuchWort("tee", testUser));  //Wort richtig
        assertFalse(neuerPool.spiel.rateVersuchWort("tei", testUser)); //Wort falsch
    }
}