package dev.b718.GeniusMusicInfo;

import java.util.Map;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeniusMusicInfo {
    private Map<String, String> env;
    private String ArtistName;

    private final String SEARCH_URI = "https://api.genius.com/search?q=";
    private final String AUTH_URI = "&access_token=";

    public GeniusMusicInfo(String artistName) {
        this.ArtistName = handleArtistName(artistName);
        env = System.getenv();
    }

    private String handleArtistName(String artistName) {
        return artistName.replace(" ", "_");
    }

    private JSONObject fetchAristData() {
        String fetchingURL = SEARCH_URI + this.ArtistName + AUTH_URI + env.get("GENIUS");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(fetchingURL)).build();

        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .join();

        JSONObject responseJson = new JSONObject(response).getJSONObject("response");
        return responseJson;
    }

    public JSONObject showArtistData() {
        return this.fetchAristData();
    }

}
