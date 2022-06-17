package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class LogikIntern {

    public static String lobby(String body) {
        JsonObject jObj = new Gson().fromJson(body, JsonObject.class);
        String nutzerName = jObj.get("name").toString();
        nutzerName = nutzerName.replace("\"", "");
        String nutzerPasswort = jObj.get("passwort").toString();
        nutzerPasswort = nutzerPasswort.replace("\"", "");

        String schwierigkeit = jObj.get("schwierigkeit").toString();
        schwierigkeit = schwierigkeit.replace("\"", "");


        Nutzer nutzer = new Nutzer(nutzerName, nutzerPasswort);

        Main.warteraum.add(nutzer);

        //Zwei Nutzer als Gegner iwie auswählen und so
        return "";
    }
}
