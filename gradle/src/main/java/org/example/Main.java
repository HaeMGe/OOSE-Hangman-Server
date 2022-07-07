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
    static ArrayList<Pool> poolListe = new ArrayList<>();
    public static ArrayList<Nutzer> nutzerListe = new ArrayList<>();
    public static ArrayList<Nutzer> warteraum = new ArrayList<>();
    //public static String[] woerter = {"Hallo", "Loesung", "Wort", "Moneymaker", "Niere", "Hangman","Schmetterling","Teppich","Regen","Tiger","Fahrrad","Dreieck","Wolke","Pinguin","Astronaut"};

    public static List<String> woerterGross, woerter;

    static {
        try {
            woerterGross = Files.readAllLines(Paths.get("woerter.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    ;

    public static void main(String[] args) throws IOException {

        woerter = new ArrayList<String>();
        //Macht alle Buchstaben aus der woerterListe Klein
        for (int i = 0; i < woerterGross.size(); i++) {
            woerter.add(woerterGross.get(i).toLowerCase());
        }
        System.out.println(woerter);


        post("/games/hangman/start/neuerNutzer", (q, a) -> {
            return RespClass.neuerNutzer(q.body());
        });

        //Rateversuche
        post("/games/hangman/start/neuesWort/0", (q, a) -> {
            return RespClass.buchstabeRaten(q.body());
        });  //Buchstabe raten
        post("/games/hangman/start/neuesWort/1", (q, a) -> {
            return RespClass.wortRaten(q.body());
        });  //Wort raten


        //Menueoptionen
        post("/games/hangman/start/neuerPool/", (q, a) -> {
            return RespClass.neuerPool(q.body());
        }); //neuen Pool anlegen
        post("/games/hangman/start/poolSuchen/", (q, a) -> {
            return RespClass.getPools();
        });  // alle Pools schicken

        //post("/games/hangman/start/meinePools/", (q, a) ->  { return RespClass.meinePools(q.body());});  //individuelle Pools schicken
        post("/games/hangman/start/beitreten/", (q, a) -> {
            return RespClass.poolBeitreten(q.body());
        });  //Pools beitreten

        //WarteRaum im Pool
        post("/games/hangman/start/pool/warteRaum", (q, a) -> {
            return RespClass.poolWarteRaum(q.body());
        });

        //Spielstatus abfragen/antwort --> wie dieses polling aufm blatt
        post("/games/hangman/start/spiel/status", (q, a) -> {
            return RespClass.status(q.body());
        });
        //Abfrage , wer als erstes anfangen darf
        post("/games/hangman/start/spiel/anfang", (q, a) -> {
            return RespClass.anfang(q.body());
        });

        //Abfrage, ob Client gewonnen hat oder nicht
        post("games/hangman/start/spiel/gewonnen", (q, a) -> {
            return RespClass.gewonnen(q.body());
        });

        //Pool loeschen, nachdem ein Spiel angefangen hat oder kein Mitspieler gefunden wurde
        post("/games/hangman/start/spiel/loeschen", (q, a) -> {
            return RespClass.PoolLoeschen(q.body());
        });


        /*Scanner sc = new Scanner(System.in);
        while (true) {
            String eingabe = sc.next();

            if (eingabe.equals("s") || eingabe.equals("stop")) {
                System.err.println("---Server gestoppt---");
                stop();
                break;
            }
        }
    }*/
    }
}
