package org.example;

import org.junit.jupiter.api.Test;
import org.example.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void neuerNutzer(){
        Nutzer nutzer1 = new Nutzer("nutzer1");
        Nutzer nutzer2 = new Nutzer("nutzer2");


        assertEquals(0, nutzer1.getPunktestand());
        assertEquals(2, Main.nutzerListe.size());


        Nutzer.startAnmeldungWarteraum(nutzer2.getName(), 1); //Nutzer 2 will ein Spiel spielen und kommt ins Wartezimmer
        assertEquals(1, Main.warteraum.size());

        Nutzer.startAnmeldungWarteraum(nutzer1.getName(), 1);  //Nutzer 1 will auch spielen

        Spiel spiel = nutzer1.spielAktuell;
        System.out.println(spiel);
        assertEquals(spiel, nutzer2.spielAktuell);  //wurde beiden Spielern dasselbe Spiel zugeordnet?

        assertEquals(0, Main.warteraum.size());

    }

}