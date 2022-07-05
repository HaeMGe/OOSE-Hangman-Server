package org.example;


import org.example.PostTesten.PostClass;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Hier wird die API-Schnittstelle getestet.
 */
public class APITest {
   static String link = "http://localhost:4567/";
    PostClass posten = new PostClass();
   // neuen Nutzer fuer ein paar Tests anlegen
   String neuerNutzerRequest = posten.doPostRequest(link+"games/hangman/start/neuerNutzer", "{ 'name': '" + "TestNutzer" + "'}");

    public APITest() throws IOException {
    }

    @Test
void neuerNutzer() throws IOException {
    String antwortServer = posten.doPostRequest(link+"games/hangman/start/neuerNutzer", "{ 'name': '" + "coolerName" + "'}"); //Namen für Nutzerliste an Server schicken
    System.out.println(antwortServer);
    assertTrue(antwortServer.contains("true")); //Wurde der neue Nutzer erfolgreich angelegt?
}
@Test
void neuerNutzerGleicherName() throws IOException {
    String antwortServer = posten.doPostRequest(link+"games/hangman/start/neuerNutzer", "{ 'name': '" + "coolerName" + "'}"); //Namen für Nutzerliste an Server schicken
    System.out.println(antwortServer);
    assertTrue(antwortServer.contains("false")); //Wurde der neue Nutzer nicht angelegt, da der Username bereits existiert?
}

@Test
void istEinPoolda() throws IOException {
    String antwort = posten.doPostRequest(link+"games/hangman/start/poolSuchen/", "pools angefragt");
    System.out.println(antwort);
    String[] antwortSplit = antwort.split("Vorhanden: ");
    assertTrue(antwortSplit[1].contains("false"));   //es sind bereits Pools im Server, weshalb mit false geantwortet wird

}
@Test
    void neuerPool() throws IOException {
    String antwort = posten.doPostRequest(link+"games/hangman/start/neuerPool/", "{ 'name': '" + "TestNutzer" + "','pool': '" + "42" + "','level': '" + 1 + "'}");  //neuen Postrequest mit Eingabe an Server
    System.out.println(antwort);
    boolean antwort2 = Boolean.parseBoolean(antwort);
    assertTrue(antwort2);
}

@Test
    void neuerPoolGleicheId() throws IOException {
    posten.doPostRequest(link+"games/hangman/start/neuerPool/", "{ 'name': '" + "TestNutzer" + "','pool': '" + "42" + "','level': '" + 1 + "'}");
    String antwort = posten.doPostRequest(link+"games/hangman/start/neuerPool/", "{ 'name': '" + "TestNutzer" + "','pool': '" + "42" + "','level': '" + 1 + "'}");  //neuen Postrequest mit Eingabe an Server
    boolean antwort2 = Boolean.parseBoolean(antwort);
    assertFalse(antwort2);
}

    @Test
    void poolLoeschen() throws IOException {
        String antwort = posten.doPostRequest(link+"games/hangman/start/spiel/loeschen", "{ 'poolID': '" + "42" + "'}");  //neuen Postrequest mit Eingabe an Server
        System.out.println(antwort);
        assertEquals("0",antwort);
    }

}
