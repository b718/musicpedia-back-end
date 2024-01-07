package dev.b718.WikiPediaPortion;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import org.json.JSONObject;

/* 
! The goal of this is to get a bio of the artist from Wikipedia
 */
public class WikiPediaPortion {
    private String ArtistName;

    public WikiPediaPortion(String artistName) {
        this.ArtistName = artistName;
    }

    private void handleArtistName() {
        this.ArtistName = this.ArtistName.replace(" ", "_");
    }

    private void formatData() {

    }

    public void getInformationOnArtist() {
        this.handleArtistName();
        String apiUrl = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&titles="
                + this.ArtistName;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // JSONObject obj = new JSONObject(response.body());
            System.out.println(response);
            System.out.println(response.body());
            // System.out.println(obj);

        } catch (Exception e) {
            System.out.println("Error" + e);
        }

    }
}
