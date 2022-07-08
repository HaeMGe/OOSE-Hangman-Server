package org.example;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.PostTesten.PostClass;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Hier wird die API-Schnittstelle getestet, indem Client-Code ausgeführt wird und somit das Senden und Erhalten von Requests und Responses
 * simuliert wird.
 */
public class APITest {
    static String link = "http://localhost:4567/";
    PostClass posten = new PostClass();   //für die Post-Requests
    // neuen Nutzer fuer ein paar Tests anlegen
    String neuerNutzerRequest = posten.doPostRequest(link + "games/hangman/start/neuerNutzer", "{ 'name': '" + "TestNutzer" + "'}");
    String neuerNutzerRequest2 = posten.doPostRequest(link + "games/hangman/start/neuerNutzer", "{ 'name': '" + "TestNutzer2" + "'}");
    String neuerNutzerRequest3 = posten.doPostRequest(link + "games/hangman/start/neuerNutzer", "{ 'name': '" + "TestNutzer3" + "'}");
    static String args[];  //Argumente für Aufruf der main(), um den Server zu starten


    public APITest() throws IOException {
    }
    //zu erst muss der Server gestartet werden
     @BeforeAll
        public static void startServer() throws IOException {
         Main.main(args);
     }

     /**
      * Hier wird ein neuer Nutzer angelegt und getestet, ob dieser erfolgreich registriert wurde.
      */
    @Test
    void neuerNutzer() throws IOException {
        String antwortServer = posten.doPostRequest(link + "games/hangman/start/neuerNutzer", "{ 'name': '" + "coolerName" + "'}"); //Namen für Nutzerliste an Server schicken
        System.out.println(antwortServer);
        assertTrue(antwortServer.contains("true")); //Wurde der neue Nutzer erfolgreich angelegt?
    }

    /**
     * Hier wird eine Falscheingabe simuliert, indem ein Nutzername verwendet wird, der bereits einem anderen User gehört.
     * @throws IOException
     */
    @Test
    void neuerNutzerGleicherName() throws IOException {
        String antwortServer = posten.doPostRequest(link + "games/hangman/start/neuerNutzer", "{ 'name': '" + "coolerName" + "'}"); //Namen für Nutzerliste an Server schicken
        System.out.println(antwortServer);
        assertTrue(antwortServer.contains("false")); //Wurde der neue Nutzer nicht angelegt, da der Username bereits existiert?
    }

    /**
     * In der Poolliste sind bereits Pools vorhanden.
     * @throws IOException
     */
    @Test
    void istEinPoolda() throws IOException {
        String antwort = posten.doPostRequest(link + "games/hangman/start/poolSuchen/", "pools angefragt");
        System.out.println(antwort);
        String[] antwortSplit = antwort.split("Vorhanden: ");
        assertTrue(antwortSplit[1].contains("false"));   //es sind bereits Pools im Server, weshalb mit false geantwortet wird
    }

    /**
     * Hier wird ein neuer Pool angelegt und überprüft, ob die Operation erfolgreich war.
     * @throws IOException
     */
    @Test
    void neuerPool() throws IOException {
        String antwort = posten.doPostRequest(link + "games/hangman/start/neuerPool/", "{ 'name': '" + "TestNutzer" + "','pool': '" + "42" + "','level': '" + 1 + "'}");  //neuen Postrequest mit Eingabe an Server
        System.out.println(antwort);
        boolean antwort2 = Boolean.parseBoolean(antwort);
        assertTrue(antwort2);
    }

    /**
     * Hier wird ebenfalls ein neuer Pool angelegt, allerdings mir bereits existierender ID --> Anlegen scheitert
     * @throws IOException
     */
    @Test
    void neuerPoolGleicheId() throws IOException {
        posten.doPostRequest(link + "games/hangman/start/neuerPool/", "{ 'name': '" + "TestNutzer" + "','pool': '" + "42" + "','level': '" + 1 + "'}");
        String antwort = posten.doPostRequest(link + "games/hangman/start/neuerPool/", "{ 'name': '" + "TestNutzer" + "','pool': '" + "42" + "','level': '" + 1 + "'}");  //neuen Postrequest mit Eingabe an Server
        boolean antwort2 = Boolean.parseBoolean(antwort);
        assertFalse(antwort2);
    }

    /**
     * Hier wird getestet, ob das Löschen eines Pools funktiniert.
     * @throws IOException
     */
    @Test
    void poolLoeschen() throws IOException {
        String antwort = posten.doPostRequest(link + "games/hangman/start/spiel/loeschen", "{ 'poolID': '" + "42" + "'}");  //neuen Postrequest mit Eingabe an Server
        System.out.println(antwort);
        assertEquals("0", antwort);
    }

    /**
     * Hier wird getestet, ob ein berechtigter Nutzer einem Pool erfolgreich beitreten kann.
     * @throws IOException
     */
    @Test
    void poolbeitreten() throws IOException {
        posten.doPostRequest(link + "games/hangman/start/neuerPool/", "{ 'name': '" + "TestNutzer" + "','pool': '" + "100" + "','level': '" + 1 + "'}");
        String antwort = posten.doPostRequest(link + "games/hangman/start/beitreten/", "{ 'name': '" + "TestNutzer2" + "','pool': '" + "100" + "'}");
        System.out.println(antwort);
        assertTrue(antwort.contains("true"));
    }

    /**
     * Hier tritt ein dritter Nutzer einem Pool bei, obwohl die maximal erlaubte Kapazität bei 2 liegt.
     * @throws IOException
     */
    @Test
    void poolbeitreten_mindestanzahl_ueberschritten() throws IOException {
        String antwort = posten.doPostRequest(link + "games/hangman/start/beitreten/", "{ 'name': '" + "TestNutzer3" + "','pool': '" + "100" + "'}");
        System.out.println(antwort);
        assertTrue(antwort.contains("false"));
    }


    @Test
    void poolWarteraum() throws IOException {
        String antwort = posten.doPostRequest(link + "games/hangman/start/pool/warteRaum", "{ 'poolID':" + "100" + " }");
        System.out.println(antwort);
        assertTrue(antwort.contains("true"));
    }


    /**
     * Hier wird das Raten eines korrekten Buchstabens simuliert.
     * @throws IOException
     */
    @Test
    void buchstabeRaten() throws IOException {
        Pool pool = new Pool(new Nutzer("megaName"), 2, 1);
        pool.spiel.geheimesWort = "testWort";   //zu erratendes Wort für den Test festlegen
        String antwort = posten.doPostRequest(link + "games/hangman/start/neuesWort/" + 0, "{ 'name': '" + "megaName" + "','pool': '" + 1 + "','zeichen': '" + 'e' + "'}");  //neuen Postrequest mit Eingabe an Server
        JsonObject jObj = new Gson().fromJson(antwort, JsonObject.class);
        String rateVersuch = jObj.get("rateVersuch").toString();
        rateVersuch = rateVersuch.replace("\"", "");
        boolean antwort2 = Boolean.parseBoolean(rateVersuch);
        System.out.println(antwort2);
        assertTrue(antwort2);
    }

    /**
     * Ein Buchstabe wird als Rateversuch an den Server geschickt und der Buchstabe ist falsch.
     * @throws IOException
     */
    @Test
    void buchstabeRatenAberFalsch() throws IOException {
        String antwort = posten.doPostRequest(link + "games/hangman/start/neuesWort/" + 0, "{ 'name': '" + "megaName" + "','pool': '" + 1 + "','zeichen': '" + 'x' + "'}");  //neuen Postrequest mit Eingabe an Server
        JsonObject jObj = new Gson().fromJson(antwort, JsonObject.class);
        String rateVersuch = jObj.get("rateVersuch").toString();
        rateVersuch = rateVersuch.replace("\"", "");
        boolean antwort2 = Boolean.parseBoolean(rateVersuch);
        System.out.println(antwort2);
        assertFalse(antwort2);
    }

    /**
     * Ein falsches Wort wird geraten und die Antwort des Server wird diesbezüglich getestet.
     * @throws IOException
     */
    @Test
    void wortRatenaberFalsch() throws IOException {
        Pool pool = new Pool(new Nutzer("megaName"), 2, 1);
        pool.spiel.geheimesWort = "testWort";
        String antwort = posten.doPostRequest(link + "games/hangman/start/neuesWort/" + 1, "{ 'name': '" + "megaName" + "','pool': '" + 1 + "','zeichen': '" + "testfalsch" + "'}");  //neuen Postrequest mit Eingabe an Server
        JsonObject jObj = new Gson().fromJson(antwort, JsonObject.class);
        String rateVersuch = jObj.get("rateVersuch").toString();
        rateVersuch = rateVersuch.replace("\"", "");
        boolean antwort2 = Boolean.parseBoolean(rateVersuch);
        System.out.println(antwort2);
        assertFalse(antwort2);
    }
}
