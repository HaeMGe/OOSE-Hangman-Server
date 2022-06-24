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
        StringBuilder pools = new StringBuilder("Alle Pools: ");
        for(Pool p: Main.poolListe){
            pools.append(p);
        }
        String info = pools.toString();
        return "{"+info+ "Gesamt: " + Main.poolListe.size()+"}";
    }

    public static String poolBeitreten(String body) {

        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String nutzerName = jObj.get("name").toString();
        nutzerName = nutzerName.replace("\"", "");

        Nutzer neuerSpieler =  null;
        for(Nutzer n: Main.nutzerListe){
            if(n.getName().equals(nutzerName)){
                neuerSpieler = n;
            }


        }

        String pool = jObj.get("pool").toString();
        pool = pool.replace("\"", "");
        int pool2 = Integer.parseInt(pool);
        Pool poolAktuell = null;
        for(Pool p : Main.poolListe){
            if(p.id == pool2){
                poolAktuell = p;
            }
            poolAktuell.mitglieder.add(neuerSpieler);
        }

        return "{ Poolbeitritt erfolgreich }";

    }


    public static String neuerNutzer(String body) {
        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String nutzerName = jObj.get("name").toString();
        nutzerName = nutzerName.replace("\"", "");
        System.out.println(nutzerName);
        Nutzer neuerNutzer = new Nutzer(nutzerName);
        Main.nutzerListe.add(neuerNutzer);
        return "{ Ihr Name wurde im System gespeichert. Sie koennen jetzt loslegen! }";
    }
}

