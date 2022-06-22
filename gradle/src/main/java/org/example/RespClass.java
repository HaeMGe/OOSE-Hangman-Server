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
            if(n.getName().equals(nutzerName))
                aktuellerNutzer = n;
        }

        String poolID = jObj.get("pool").toString();
        poolID = poolID.replace("\"", "");
        int pool = Integer.parseInt(poolID);

        String schwierigkeit = jObj.get("level").toString();
        schwierigkeit = schwierigkeit.replace("\"", "");
        int level = Integer.parseInt(schwierigkeit);
        Pool neuerPool = new Pool(aktuellerNutzer, level, pool);
        Main.poolListe.add(neuerPool);
        for(Pool p : Main.poolListe) {
            return p.id != pool;    //ist ID schon vergeben?
        }
        return false;
    }

    public static String getPools(){
        StringBuilder pools = new StringBuilder("Alle Pools: ");
        for(Pool p: Main.poolListe){
            pools.append(p);
        }
        return null;
    }
}
