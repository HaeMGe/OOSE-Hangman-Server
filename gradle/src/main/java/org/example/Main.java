package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static spark.Spark.*;

public class Main {
   static  ArrayList<Pool> poolListe = new ArrayList<>();
    public static ArrayList<Nutzer> nutzerListe = new ArrayList<>();
    public static ArrayList<Nutzer> warteraum = new ArrayList<>();
    //public static String[] woerter = {"Hallo", "Loesung", "Wort", "Moneymaker", "Niere", "Hangman","Schmetterling","Teppich","Regen","Tiger","Fahrrad","Dreieck","Wolke","Pinguin","Astronaut"};

    public static List<String> woerter;

    public static void main(String[] args) throws IOException {

        //1000 wörter oder so aus der .txt datei in liste tun
        woerter = Files.readAllLines(Paths.get("gradle/src/main/java/org/example/woerter.txt"));

        System.out.println(woerter);

        //port(5741);

        Nutzer init = new Nutzer("init");
        Main.nutzerListe.add(init);
        //nur zum Testen
        Pool poolTest = new Pool(init, 1, 123);
        RespClass.poolWarteRaum("{ 'poolID':"+123+" }");


        post("/games/hangman/start", (q, a) -> "{ 'text' : 'Herzlich Willkommen vom Server!' }");
        post("/games/hangman/start/neuerNutzer", (q, a) -> { return RespClass.neuerNutzer(q.body());});



        //Rateversuche
        post("/games/hangman/start/neuesWort/0", (q, a) -> {return RespClass.buchstabeRaten(q.body());});  //Buchstabe raten
        post("/games/hangman/start/neuesWort/1", (q, a) -> {return RespClass.wortRaten(q.body());});  //Wort raten


        //Menueoptionen
        post("/games/hangman/start/neuerPool/", (q, a) -> { return RespClass.neuerPool(q.body());}); //neuen Pool anlegen
        post("/games/hangman/start/poolSuchen/", (q, a) ->  { return RespClass.getPools();});  // alle Pools schicken
        post("/games/hangman/start/meinePools/", (q, a) ->  { return RespClass.meinePools(q.body());});  //individuelle Pools schicken
        post("/games/hangman/start/beitreten/", (q, a) ->  {return RespClass.poolBeitreten(q.body());});  //Pools beitreten

        //WarteRaum im Pool
        post("/games/hangman/start/pool/warteRaum", (q,a) -> {return RespClass.poolWarteRaum(q.body());});

        //Spielstatus abfragen/antwort --> wie dieses polling aufm blatt
        post("/games/hangman/start/spiel/status", (q,a) -> {return RespClass.status(q.body());});
        //Abfrage , wer als erstes anfangen darf
        post("/games/hangman/start/spiel/anfang", (q,a) -> {return RespClass.anfang(q.body());});

        post("/games/hangman/start/lobby",(q, a)-> LogikIntern.lobby(q.body()));

        Scanner sc = new Scanner(System.in);
    while(true) {
        String eingabe = sc.next();

        if (eingabe.equals("s") || eingabe.equals("stop")) {
            System.err.println("---Server gestoppt---");
            stop();
            break;
    }
}


       // get("/games/hangman/start/loesen","application/json", ((request, response) -> {
          //  response.type("application/json");

        //    return  request.params(":id");
    //    }));

    }
}