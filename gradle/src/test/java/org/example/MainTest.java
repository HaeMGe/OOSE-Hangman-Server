package org.example;

import org.junit.jupiter.api.Test;
import org.example.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void neuerPoolundNutzer(){
        Nutzer testUser = new Nutzer("test");
        Pool pool = new Pool(testUser, 2, 123);
        assertEquals(2,pool.spiel.schierigkeitsgrad);  //wurde der Schwierigkeitsgrad angepasst und ein passendes Spiel angelegt?
        assertEquals(1, pool.anzahlSpieler());         //ist bisher nur der Initiator im Pool?
        Nutzer neuerNutzer = new Nutzer("neuer");
        assertEquals(0,neuerNutzer.leben);
        pool.spiel.members.add(neuerNutzer);
        assertEquals(2, pool.anzahlSpieler());
    }


}