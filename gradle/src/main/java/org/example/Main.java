package org.example;

import java.util.ArrayList;

import static spark.Spark.*;

public class Main {
   static  ArrayList<Pool> poolListe = new ArrayList<>();
    public static ArrayList<Nutzer> nutzerListe = new ArrayList<>();
    public static ArrayList<Nutzer> warteraum = new ArrayList<>();
    public static String[] woerter = {"Hallo", "Loesung", "Wort", "Moneymaker", "Niere", "Hangman"};
    public static void main(String[] args) {


        post("/games/hangman/start", (q, a) -> "{ 'text' : 'Herzlich Willkommen vom Server!' }");
        post("/games/hangman/start/neuerNutzer", (q, a) -> { return RespClass.neuerNutzer(q.body());});



        //Rateversuche
        post("/games/hangman/start/neuesWort/0", (q, a) -> {return RespClass.buchstabeRaten(q.body());});  //Buchstabe raten
        post("/games/hangman/start/neuesWort/1", (q, a) -> "{ Wort angekommen! }");  //Wort raten


        //Menueoptionen
        post("/games/hangman/start/neuerPool/", (q, a) -> { return RespClass.neuerPool(q.body());}); //neuen Pool anlegen
        post("/games/hangman/start/poolSuchen/", (q, a) ->  { return RespClass.getPools();});  //Pools schicken
        post("/games/hangman/start/beitreten/", (q, a) ->  {return RespClass.poolBeitreten(q.body());});  //Pools beitreten

        //WarteRaum im Pool
        post("/games/hangman/start/pool/warteRaum", (q,a) -> {return RespClass.poolWarteRaum(q.body());});

        //Spielstatus abfragen/antwort --> wie dieses polling aufm blatt
        post("/games/hangman/start/spiel/status", (q,a) -> {return RespClass.status(q.body());});
        //Abfrage , wer als erstes anfangen darf
        post("/games/hangman/start/spiel/anfang", (q,a) -> {return RespClass.anfang(q.body());});

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