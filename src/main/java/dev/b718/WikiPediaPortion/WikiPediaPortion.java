package dev.b718.WikiPediaPortion;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;
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

    private void parseData(String responseBody) {
        JSONObject ArtistObject = new JSONObject(responseBody);
        Set<String> ArtistKeySet = ArtistObject.getJSONObject("query").getJSONObject("pages").keySet();

        String HtmlData = ArtistObject.getJSONObject("query").getJSONObject("pages")
                .getJSONObject(ArtistKeySet.iterator().next())
                .getString("extract");

        System.out.println(HtmlData);

    }

    public void getInformationOnArtist() {
        this.handleArtistName();
        String apiUrl = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&titles="
                + this.ArtistName;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();

        try {
            String result = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body).join();

            parseData(result);

        } catch (Exception e) {
            System.out.println("Error" + e);
        }

    }
}
