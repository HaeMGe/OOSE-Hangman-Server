package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class RespClass {
    public static String buchstabeRaten(String body) {
        Nutzer spieler = null;
        System.out.println(body);
        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String nutzerName = jObj.get("name").toString();
        nutzerName = nutzerName.replace("\"", "");
        for(Nutzer n: Main.nutzerListe){
            if(n.getName().equals(nutzerName)){
                spieler = n;
            }
            else return "Spieler nicht vorhanden";
        }
        System.out.println("nach nutzer");
        String poolNr = jObj.get("pool").toString();
        poolNr = poolNr.replace("\"", "");
        int pool = Integer.parseInt(poolNr);  //Poolnr
        System.out.println("in pool");
        Pool poolAktuell = Main.poolListe.get(pool);
        System.out.println("nach Pool");

        String zeichen = jObj.get("zeichen").toString();
        zeichen = zeichen.replace("\"", "");
        System.out.println(zeichen);
        System.out.println("nach zeichen");

        if(poolAktuell.spiel.amZugIndex ==1){
            poolAktuell.spiel.amZugIndex =0;
        }else{
            poolAktuell.spiel.amZugIndex =1;
        }

        boolean erfolg = poolAktuell.spiel.rateVersuchChar(zeichen.charAt(0), spieler);
        return "{" + erfolg + "}";
    }

    public static boolean neuerPool(String body) {
        System.out.println(body);
        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String nutzerName = jObj.get("name").toString();
        nutzerName = nutzerName.replace("\"", "");
        Nutzer aktuellerNutzer = null;
        for(Nutzer n: Main.nutzerListe){
            if(n.getName().equals(nutzerName)) {
                aktuellerNutzer = n;
                System.out.println(n);
            }
        }

        String poolID = jObj.get("pool").toString();
        poolID = poolID.replace("\"", "");
        int pool = Integer.parseInt(poolID);
        System.out.println(pool);

        String schwierigkeit = jObj.get("level").toString();
        schwierigkeit = schwierigkeit.replace("\"", "");
        int level = Integer.parseInt(schwierigkeit);
        System.out.println(level);

        for(Pool p : Main.poolListe) {
            if (p.id == pool){  //id schon vorhanden
                return false;
            }
        }
        Pool neuerPool = new Pool(aktuellerNutzer, level, pool);  //alles ok, neuer Pool kann angelegt werden
        return true;
    }

    public static String getPools(){
        StringBuilder pools = new StringBuilder();
        for(Pool p: Main.poolListe){
            pools.append(p);
        }
        String info = pools.toString();
        boolean empty = false;
        if(Main.poolListe.size()== 0){
            empty = true;
        }
        return "{"+info+ "Vorhanden: " + empty+"}";
    }

    public static String poolBeitreten(String body) {
        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String nutzerName = jObj.get("name").toString();
        nutzerName = nutzerName.replace("\"", "");

        //Spieler in Liste finden
        Nutzer neuerSpieler =  null;
        for(Nutzer n: Main.nutzerListe){
            if(n.getName().equals(nutzerName)){
                neuerSpieler = n;
            }
        }

        //Pool finden
        String pool = jObj.get("pool").toString();
        pool = pool.replace("\"", "");
        int pool2 = Integer.parseInt(pool);

        Pool poolAktuell = null;
        for(Pool p : Main.poolListe) {
            if (p.id == pool2) {
                poolAktuell = p;
            }
        }


        //Testten, ob Beitritt möglich
        if(neuerSpieler!=null && poolAktuell != null){

            //Ist Nutzer bereits im Pool?
            for(Nutzer n: poolAktuell.spiel.members) {
                if (n.getName().equals(neuerSpieler.getName())) {
                    System.out.println(n.getName());
                    System.out.println(neuerSpieler.getName());
                    return "{ Erfolg: " + false + "}";
                }
            }

            if(poolAktuell.anzahlSpieler() !=2){
                poolAktuell.spiel.members.add(neuerSpieler);  //Spieler dem Pool hinzufügen
                return "{ Erfolg: " + true+ "}";
            }else{
                return "{ Erfolg: " + false+ "}";
            }
        }

        return " ende ";
    }


    public static String neuerNutzer(String body) {
        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String nutzerName = jObj.get("name").toString();
        nutzerName = nutzerName.replace("\"", "");
        System.out.println(nutzerName);
        Nutzer neuerNutzer = new Nutzer(nutzerName);
        Main.nutzerListe.add(neuerNutzer);
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

    public static String status(String body) {
        //fürs polling, gibt alle infos zum spiel
        System.out.println(body);

        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String name = jObj.get("name").toString();
        name = name.replace("\"", "");

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

        boolean amZug = poolAktuell.spiel.istAmZug(name);

        String statusText = poolAktuell.spiel.spielStatus(name);


        System.out.println("{'amZug':'"+amZug+"',"+statusText+"}");
        return "{'amZug':'"+amZug+"',"+statusText+"}";
    }

    /**
     * Gibt dem Client zurück, ob er oder der Gegner anfangen darf zu Raten
     * @param body JSON mit der PoolID und dem Namen des Nutzers
     * @return boolean, welches angibt, ob der Client anfangen darf zu raten oder nicht
     */
    public static String anfang(String body) {

        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String poolID = jObj.get("poolID").toString();
        poolID = poolID.replace("\"", "");
        int poolID2 = Integer.parseInt(poolID);

        String name = jObj.get("name").toString();
        name = name.replace("\"", "");

        Pool poolAktuell = null;
        for(Pool p : Main.poolListe) {
            if (p.id == poolID2) {
                poolAktuell = p;
            }
        }

        //Der erste Nutzer in der Liste vom Pool (der Nutzer der den pool erstellt hat im endeffekt) darf anfangen
        if(poolAktuell.spiel.members.get(0).getName().equals(name)){
            return "{'anfang':'true'}";
        }else{
            return "{'anfang':'false'}";
        }
    }

    public static String meinePools(String body) {
        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String nutzerName = jObj.get("name").toString();
        nutzerName = nutzerName.replace("\"", "");

        //Spieler in Liste finden
        Nutzer neuerSpieler =  null;
        for(Nutzer n: Main.nutzerListe){
            if(n.getName().equals(nutzerName)){
                neuerSpieler = n;
            }
        }
        System.out.println(neuerSpieler);
        StringBuilder s = new StringBuilder();
        for(Pool p: Main.poolListe){
            if(p.spiel.members.contains(neuerSpieler)){
                s.append(p);
            }
        }
        return s.toString();
    }

    public static String wortRaten(String body) {
        return "";
    }
}

