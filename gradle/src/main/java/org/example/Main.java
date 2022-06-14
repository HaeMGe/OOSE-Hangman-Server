package org.example;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


        post("/games/hangman/start", (q, a) -> "{ Herzlich Willkommen vom Server! }");

    }
}