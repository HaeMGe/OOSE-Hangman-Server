package org.example;

import okhttp3.*;
import okio.BufferedSink;
import org.example.Main;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Hier wird die API-Schnittstelle getestet.
 */
public class APITest {
    // avoid creating several instances, should be singleton
    OkHttpClient client = new OkHttpClient();

    //Request request = new Request.Builder()
     //       .url("http://localhost:4567/")
      //      .build();

   static String link = "http://localhost:4567/";
@Test
void neuerNutzer() throws IOException {

    HttpUrl.Builder urlBuilder = HttpUrl.parse(link+"games/hangman/start/neuerNutzer").newBuilder();
  //  urlBuilder.addQueryParameter("name", "neuerName");

  //  urlBuilder.addQueryParameter("user", "vogella");
    String url = urlBuilder.build().toString();

    Request request = new Request.Builder()
            .url(url)
            .build();



    Response response = client.newCall(request).execute();

   // System.out.println(Main.nutzerListe);
   // System.out.println(Main.nutzerListe.size());
    System.out.println(response);


    //String antwortServer = Main.posten.doPostRequest(link+"games/hangman/start/neuerNutzer", "{ 'name': '" + Main.name + "'}"); //Namen für
}
}
