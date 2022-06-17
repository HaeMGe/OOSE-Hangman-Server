package org.example;

import java.util.ArrayList;

import static spark.Spark.*;

public class Main {
    public static ArrayList<Nutzer> nutzerListe = new ArrayList<>();
    public static ArrayList<Nutzer> warteraum = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println("Hello world!");


        post("/games/hangman/start", (q, a) -> "{ Herzlich Willkommen vom Server! }");
        post("/games/hangman/start/neuesWort", (q, a) -> "{ Wort angekommen! }");

        post("/games/hangman/start/lobby",(q, a)-> LogikIntern.lobby(q.body()));

       // get("/games/hangman/start/loesen","application/json", ((request, response) -> {
          //  response.type("application/json");

        //    return  request.params(":id");
    //    }));

    }


}