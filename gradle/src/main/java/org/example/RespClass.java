package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class RespClass {

    /**
     * Ein Buchstabe wird vom CLient eingegeben und an den Server geschickt. Hier wird überprüft, ob der Rateversuch korrekt ist.
     * @param body geratener Buchstabe, Name und Pool des Nutzers bzw Spieles
     * @return Rückmeldung, ob Versuch erfolgreich --> true, sonst false
     */
    public static String buchstabeRaten(String body) {
        Nutzer spieler = null;
        //System.out.println(body);

        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String nutzerName = jObj.get("name").toString();   //Name der ratenden Person
        nutzerName = nutzerName.replace("\"", "");

        String poolString = jObj.get("pool").toString();   //Pool des zugehörigen Spieles
        poolString = poolString.replace("\"", "");

        int poolID = Integer.parseInt(poolString);   //Pool ID von String zu Integer parsen

        Pool p = null;
        for (Pool l : Main.poolListe) {  //Pool aus der Poolliste finden
            if (l.id == poolID) {
                p = l;
            }
        }

        assert p != null;
        for (Nutzer n : p.spiel.members) {   //Spieler in Nutzerliste finden
            if (n.getName().equals(nutzerName)) {
                spieler = n;
            }
        }


            String zeichen = jObj.get("zeichen").toString();  //den Rateversuch auslesen
            zeichen = zeichen.replace("\"", "");

            if (p.spiel.amZugIndex == 1) {   //die beiden Spieler wechseln sich ab mit dem Raten --> amZug-Flag ändern
                p.spiel.amZugIndex = 0;
            } else {
                p.spiel.amZugIndex = 1;
            }
            System.err.println(p.spiel.amZugIndex);

            boolean erfolg = p.spiel.rateVersuchChar(zeichen.charAt(0), spieler);   //true, wenn Versuch richtig, sonst false
            return "{'rateVersuch':'" + erfolg + "'}";
        }


    /**
     * Ein neuer Pool wird angelegt, der Inititator ist sofort Mitglied des Pools.
     * @param body Initiator, Pool-ID und gewünschter Schwierigkeitsgrad des Spieles
     * @return true, wenn das Anlegen erfolgreich war, sonst false
     */
    public static boolean neuerPool(String body) {
        System.out.println(body);
        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String nutzerName = jObj.get("name").toString();   //Spielername auslesen
        nutzerName = nutzerName.replace("\"", "");
        Nutzer aktuellerNutzer = null;
        for(Nutzer n: Main.nutzerListe){   //Spieler in Nutzerliste suchen
            if(n.getName().equals(nutzerName)) {
                aktuellerNutzer = n;
                System.out.println(n);
            }
        }

        String poolID = jObj.get("pool").toString();   //PoolID auslesen
        poolID = poolID.replace("\"", "");
        int pool = Integer.parseInt(poolID);
        System.out.println(pool);

        String schwierigkeit = jObj.get("level").toString();  //Schwierigkeitsgrad auslesen
        schwierigkeit = schwierigkeit.replace("\"", "");
        int level = Integer.parseInt(schwierigkeit);
        System.out.println(level);

        for(Pool p : Main.poolListe) {   //Überprüfung, ob diese PoolID bereits vergeben ist
            if (p.id == pool){  //id schon vorhanden
                return false;
            }
        }
        System.out.println("neuer Pool wird angelegt");
        //alle Tests bestanden --> neuer Pool kann jetzt angelegt werden
        Pool neuerPool = new Pool(aktuellerNutzer, level, pool);  //alles ok, neuer Pool kann angelegt werden
        System.out.println("anlegen erfolgt");
        return true;
    }

    /**
     * Alle Pools, die zur Zeit offen sind, werden dem Client als String geschickt
     * @return String mit allen Pools und Info, ob es Pools gibt oder nicht --> vorhanden ist true, wenn Liste leer ist, sonst false
     */
    public static String getPools(){
        StringBuilder pools = new StringBuilder();  //hier werden alle Pools als String aneinander gereiht
        for(Pool p: Main.poolListe){
            pools.append(p);  //jeden Pool dem String anhaengen
        }
        String info = pools.toString();   //StringBuilder --> String
        boolean empty = false;
        if(Main.poolListe.size()== 0){   //Gibt es keine Pools?
            empty = true;
        }
        return "{"+info+ "Vorhanden: " + empty+"}";
    }

    /**
     * Der Nutzer kann einem Pool beitreten, indem die Pool ID ausgewählt wird.
     * @param body Nutzername und PoolID, die gewaehlt wurde
     * @return Erfolg: true, wenn Beitritt erfolgreich, sonst false
     */
    public static String poolBeitreten(String body) {
        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String nutzerName = jObj.get("name").toString();   //Name auslesen
        nutzerName = nutzerName.replace("\"", "");

        //Spieler in Liste finden
        Nutzer neuerSpieler =  null;
        for(Nutzer n: Main.nutzerListe){   //nach Spieler in der Nutzerliste suchen
            if(n.getName().equals(nutzerName)){
                neuerSpieler = n;
            }
        }

        //Pool finden
        String pool = jObj.get("pool").toString();   //Pool auslesen
        pool = pool.replace("\"", "");
        int pool2 = Integer.parseInt(pool);

        Pool poolAktuell = null;
        for(Pool p : Main.poolListe) {   //nach aktuellem Pool in der Poolliste suchen
            if (p.id == pool2) {
                poolAktuell = p;
            }
        }

        //Testen, ob Beitritt möglich
        if(neuerSpieler!=null && poolAktuell != null){   //wurde ein existierender Pool gewählt?

            //Ist Nutzer bereits im Pool?
         /**   for(Nutzer n: poolAktuell.spiel.members) {
                if (n.getName().equals(neuerSpieler.getName())) {  //ist man dem Pool bereits beigetreten?
                    return "{ Erfolg: " + false + "}";
                }
            }
          */

            if(poolAktuell.anzahlSpieler() !=2){
                neuerSpieler.setLeben(poolAktuell.spiel.leben);
                poolAktuell.spiel.members.add(neuerSpieler);  //Spieler dem Pool hinzufügen
                return "{ Erfolg: " + true+ "}";
            }else{
                return "{ Erfolg: " + false+ "}";
            }
        }

        return " ende ";
    }

    /**
     * Erstellt den neuen Nutzer, falls es noch keinen mit demselben Namen gibt
     * @param body Name  des zu neuen erstellenden Nutzers als JSON
     * @return gibt zurück, ob der neue Nutzer erstell werden konnte
     */
    public static String neuerNutzer(String body) {

        System.out.println("Neuen Nutzer anlegen");
        //Holt Name aus der JSON
        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String nutzerName = jObj.get("name").toString();
        nutzerName = nutzerName.replace("\"", "");

        //Überprüfung, ob es nicht schon einen Nutzer mit demselben nutzernamen gibt
        for(Nutzer n : Main.nutzerListe){
            if(n.getName().equals(nutzerName)){
                System.out.println("Fehler, "+nutzerName+" existiert schon");
                return "{ 'neuerNutzer': 'false' }";
            }
        }

        Nutzer neuerNutzer = new Nutzer(nutzerName);
        //Fügt den neuen Nutzer in die Nutzerliste ein
        Main.nutzerListe.add(neuerNutzer);
        System.out.println("Nutzer :"+nutzerName+" erstellt");

        return "{ 'neuerNutzer': 'true' }";
    }

    /**
     * Die Methode gibt auf Anfrage vom Client zurück, ob der Pool 2 Spieler hat, damit das Spiel beginnen kann
     * Der Client ruft diese Methode einmal pro Sekunde uaf, bis ein Gegner dem Pool beitritt
     * @param body poolID, um zu überprüfen ob 2 Spieler in dem Pool sind
     * @return boolean, welcher angibt, ob nun ein Gegner in dem Pool ist und das Spiel gestartet werden kann oder nicht
     */
    public static String poolWarteRaum(String body) {

        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String poolID = jObj.get("poolID").toString();
        poolID = poolID.replace("\"", "");
        int poolID2 = Integer.parseInt(poolID);

        //Sucht den entsprechenden Pool raus
        Pool poolAktuell = null;
        for(Pool p : Main.poolListe) {
            if (p.id == poolID2) {
                poolAktuell = p;
            }
        }

        assert poolAktuell != null;
        //Prüft, ob 2 Leute in dem Pool sind, damit das Spiel beginnen kann
        if(poolAktuell.anzahlSpieler() == 2){
            return "{true}";
        }else{
            return "{false}";
        }
    }

    /**
     * Gibt dem Client alle relevanten Informationen zu dem Spiel zurück, wird 1x die Sekunde vom Client erzeugt.
     * @param body Name und PoolID des Nutzers werden übergeben
     * @return gibt zurück, ob der Nutzer am Zug ist, die Anzahl der Leben,
     * die Menge der falschen Eingaben,
     * ob das Spiel vorbei ist und was erraten wurde
     */
    public static String status(String body) {
        System.out.println(body);

        //Holt name und PoolID aus der JSON raus
        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String name = jObj.get("name").toString();    //Nutzername auslesen
        name = name.replace("\"", "");

        String poolID = jObj.get("poolID").toString();   //PoolID auslesen
        poolID = poolID.replace("\"", "");
        int poolID2 = Integer.parseInt(poolID);

        //Sucht den entsprechenden Pool raus
        Pool poolAktuell = null;
        for(Pool p : Main.poolListe) {   //nach Pool in der Poolliste suchen
            if (p.id == poolID2) {
                poolAktuell = p;
            }
        }

       if(poolAktuell == null){
           return"{'amZug':'false','erraten':'','leben':'0','spielVorbei':'true','fehlversuche':'','fehlversucheWort':'','poolVorhanden':'false'}";
       }
        //Gibt zurück, ob der Nutzer am Zug ist
        boolean amZug = poolAktuell.spiel.istAmZug(name);

        //Gibt den Rest des StatusText zurück, ink. anzahl Leben, falsche Eingaben, richtige Eingaben, ob das Spiel vorbei ist
        String statusText = poolAktuell.spiel.spielStatus(name);

        return "{'amZug':'"+amZug+"',"+statusText+"}";
    }

    /**
     * Gibt dem Client zurück, ob er oder der Gegner anfangen darf zu Raten
     * @param body JSON mit der PoolID und dem Namen des Nutzers
     * @return boolean, welches angibt, ob der Client anfangen darf zu raten oder nicht
     */
    public static String anfang(String body) {

        //PoolID und Name werden aus der JSON rausgeholt
        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String poolID = jObj.get("poolID").toString();
        poolID = poolID.replace("\"", "");
        int poolID2 = Integer.parseInt(poolID);

        String name = jObj.get("name").toString();
        name = name.replace("\"", "");

        //Der Pool wird rausgesucht
        Pool poolAktuell = null;
        for(Pool p : Main.poolListe) {
            if (p.id == poolID2) {
                poolAktuell = p;
            }
        }

        //Prüft ob der Pool überhaupt existiert
        if(poolAktuell==null){
            System.err.println("Fehler beim aufrufen des Pools");
            return "{'anfang':'false'}";
        }

        //Der erste Nutzer in der Liste vom Pool (der Nutzer der den pool erstellt hat im endeffekt) darf anfangen
        if(poolAktuell.spiel.members.get(0).getName().equals(name)){

            poolAktuell.spiel.members.get(0).setLeben(poolAktuell.spiel.leben);
            poolAktuell.spiel.members.get(1).setLeben(poolAktuell.spiel.leben);

            return "{'anfang':'true'}";
        }else{
            return "{'anfang':'false'}";
        }
    }


    /**
     * Methode, welche aufgerufen wird, falls der Spieler direkt ein Wort raten will
     * @param body Name des Spielers, PoolID und das Wort das der Spieler geraten hat
     * @return Gibt boolean zurück, ob das Wort richtig geraten wurde oder nicht
     */
    public static String wortRaten(String body) {
        Nutzer spieler = null;
        System.out.println(body);

        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String nutzerName = jObj.get("name").toString();
        nutzerName = nutzerName.replace("\"", "");

        String poolString = jObj.get("pool").toString();
        poolString = poolString.replace("\"", "");

        int poolID = Integer.parseInt(poolString);

        Pool p = null;

        //Prüft, ob der pool existiert
        for (Pool l : Main.poolListe) {
            if (l.id == poolID) {
                p = l;
            }
        }

        assert p != null;
        for (Nutzer n : p.spiel.members) {
            if (n.getName().equals(nutzerName)) {
                spieler = n;
            }
        }

        //Prüft ob der Spieler existiert
        if(spieler ==null){
            System.err.println("Spieler nicht vorhanden");
            return "Spieler nicht vorhanden";
        }

        //Holt das zu prüfende Wort aus der JSON
        String wort = jObj.get("zeichen").toString();
        wort = wort.replace("\"", "");
        System.out.println(wort);

        //Ändert den Zustand des Spiels, sodass der anderer Spieler nun dran ist
        if (p.spiel.amZugIndex == 1) {
            p.spiel.amZugIndex = 0;
        } else {
            p.spiel.amZugIndex = 1;
        }

        System.err.println(p.spiel.amZugIndex);

        //Boolean, ob das Wort richtig geraten wurde oder nicht
        boolean erfolg = p.spiel.rateVersuchWort(wort, spieler);

        return "{'rateVersuch':'" + erfolg + "'}";
    }


    /**
     * Löscht den Pool des angegebenen Pools
     * @param body PoolID des zu löschenden Pools
     * @return Gibt zurück, ob der Pool gelöscht wurde oder nicht
     */
    public static int PoolLoeschen(String body){
        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String poolString = jObj.get("poolID").toString();    //PoolID auslesen
        poolString = poolString.replace("\"", "");

        int poolID = Integer.parseInt(poolString);   //PoolID zu Integer parsen


        //Pool loeschen
        for (Pool p : Main.poolListe){
            if(p.id == poolID){
                Main.poolListe.remove(p);   //Pool aus Poolliste entfernen
                System.out.println("geloescht");
                return 0;
            }
        }
        System.out.println("nichtgeloescht");
        return -1;
    }

    /**
     * Gibt zurück, ob der Spieler gewonnen hat oder nicht
     * ---Wird nicht benutzt---
     * @param body Name des Spielers und des Pools
     * @return Gibt boolean zurück, ob der Spieler gewonnen hat
     */
    public static String gewonnen(String body) {

        Nutzer spieler = null;
        System.out.println(body);

        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String nutzerName = jObj.get("name").toString();
        nutzerName = nutzerName.replace("\"", "");

        String poolString = jObj.get("pool").toString();
        poolString = poolString.replace("\"", "");

        int poolID = Integer.parseInt(poolString);

        Pool p = null;

        for (Pool l : Main.poolListe) {
            if (l.id == poolID) {
                p = l;
            }
        }

        assert p != null;
        for (Nutzer n : p.spiel.members) {
            if (n.getName().equals(nutzerName)) {
                spieler = n;
            }
        }

        if(spieler ==null){
            System.err.println("Spieler nicht vorhanden");
            return "Spieler nicht vorhanden";
        }

        if (p.spiel.amZugIndex == 1) {
            p.spiel.amZugIndex = 0;
        } else {
            p.spiel.amZugIndex = 1;
        }

        if(p.spiel.members.get(0).leben == 0 && p.spiel.members.get(0).getName().equals(nutzerName)){
            return"{'gewonnen':'false'}";
        }
        if(p.spiel.members.get(0).leben == 0 && !(p.spiel.members.get(0).getName().equals(nutzerName))){
            return"{'gewonnen':'true'}";
        }
        if(p.spiel.members.get(1).leben == 0 && p.spiel.members.get(1).getName().equals(nutzerName)){
            return"{'gewonnen':'false'}";
        }
        if(p.spiel.members.get(1).leben == 0 && !(p.spiel.members.get(1).getName().equals(nutzerName))){
            return"{'gewonnen':'true'}";
        }

        boolean geraten = true;

        for(int i = 0;i<p.spiel.geheimesWort.length();i++){

            if(p.spiel.geheimesWort.charAt(i) == p.spiel.erraten[i]){

            }else{
                geraten = false;
            }

        }

        if(geraten){
            if (p.spiel.amZugIndex == 0) {
                if(p.spiel.members.get(0).getName().equals(nutzerName)){
                    return"{'gewonnen':'true'}";
                }else{
                    return"{'gewonnen':'false;'}";
                }
            }else{
                if(p.spiel.members.get(1).getName().equals(nutzerName)){
                    return"{'gewonnen':'true'}";
                }else{
                    return"{'gewonnen':'false;'}";
                }
            }
        }

        return"{'gewonnen':'false'}";
    }
}


