package org.example;

import okhttp3.*;
import org.example.PostTesten.PostClass;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

/**
 * Hier wird die API-Schnittstelle getestet.
 */
public class APITest {
   static String link = "http://localhost:4567/";
@Test
void neuerNutzer() throws IOException {
    PostClass posten = new PostClass();
   String response =  posten.doPostRequest(link + "games/hangman/tests", "");
    System.out.println(response);


}
}
