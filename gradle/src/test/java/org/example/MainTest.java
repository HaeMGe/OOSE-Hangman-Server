package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    Nutzer testUser = new Nutzer("test");
    static String[] args;
    @BeforeAll
    public static void startServer() throws IOException {
        Main.main(args);
    }
    @Test
   void neuerPoolundNutzer(){
       // Nutzer testUser = new Nutzer("test");
        Pool pool = new Pool(testUser, 2, 123);
        assertEquals(2,pool.spiel.schierigkeitsgrad);  //wurde der Schwierigkeitsgrad angepasst und ein passendes Spiel angelegt?
        assertEquals(1, pool.anzahlSpieler());         //ist bisher nur der Initiator im Pool?
        Nutzer neuerNutzer = new Nutzer("neuer");
        assertEquals(0,neuerNutzer.leben);
        pool.spiel.members.add(neuerNutzer);
        assertEquals(2, pool.anzahlSpieler());
    }


    @Test
    void richtigenUndFalschenBuchstabenRaten(){
        Pool neuerPool = new Pool(testUser, 2, 12);  //neuen Pool anlegen
        neuerPool.spiel.geheimesWort = "testWort";
        assertTrue(neuerPool.spiel.rateVersuchChar('e', testUser));  //Buchstabe drin
        assertFalse(neuerPool.spiel.rateVersuchChar('l', testUser)); //Buchstabe nicht drin
    }

    @Test
    void richtigesUndFalschesWortRaten(){
        Pool neuerPool = new Pool(testUser, 2, 12);  //neuen Pool anlegen
        neuerPool.spiel.geheimesWort = "testWort";
        assertTrue(neuerPool.spiel.rateVersuchWort("testWort", testUser));  //Buchstabe drin
        assertFalse(neuerPool.spiel.rateVersuchWort("testWortFalsch", testUser)); //Buchstabe nicht drin
    }
}