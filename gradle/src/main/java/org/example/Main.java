package org.example;

import java.util.ArrayList;

import static spark.Spark.*;

public class Main {
   static  ArrayList<Pool> poolListe = new ArrayList<>();
    public static ArrayList<Nutzer> nutzerListe = new ArrayList<>();
    public static ArrayList<Nutzer> warteraum = new ArrayList<>();
    public static String[] woerter = {"Hallo", "Loesung", "Wort", "Moneymaker", "Niere", "Hangman"};
    public static void main(String[] args) {
        poolListe.add(new Pool(new Nutzer("ich"), 0, 7));
        System.out.println("Hello world!");


        post("/games/hangman/start", (q, a) -> "{ Herzlich Willkommen vom Server! }");
        post("/games/hangman/start/neuesWort/0", (q, a) -> {return RespClass.buchstabeRaten(q.body());});  //Buchstabe raten
        post("/games/hangman/start/neuesWort/1", (q, a) -> "{ Wort angekommen! }");  //Wort raten

        post("/games/hangman/start/lobby",(q, a)-> LogikIntern.lobby(q.body()));



       // get("/games/hangman/start/loesen","application/json", ((request, response) -> {
          //  response.type("application/json");

        //    return  request.params(":id");
    //    }));


        //Versuch:


      //  Nutzer user1 = new Nutzer("Nutzer1", "2342");
      //  Nutzer user2 = new Nutzer("Nutzer2", "444");

       // Spiel spiel = new Spiel(user1, user2, 1);
    }


}