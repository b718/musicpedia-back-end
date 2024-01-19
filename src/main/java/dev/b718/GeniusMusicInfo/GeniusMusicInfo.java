package dev.b718.GeniusMusicInfo;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeniusMusicInfo {
    private Map<String, String> env;
    private String ArtistName;
    private JSONObject ArtistData;
    private String ArtistID;
    private JSONArray AlterateNames;
    private JSONArray Songs;
    private JSONObject GeniusFinalObject;

    private final String URI_BASE = "https://api.genius.com/";
    private final String SEARCH_URI = URI_BASE + "search?q=";
    private final String AUTH_URI = "&access_token=";
    private final String ARTIST_URI = URI_BASE + "artists/";

    public GeniusMusicInfo(String artistName) {
        this.ArtistName = handleArtistName(artistName);
        this.ArtistData = new JSONObject();
        env = System.getenv();
    }

    private String handleArtistName(String artistName) {
        return artistName.replace(" ", "_");
    }

    private void fetchAristData() {
        String fetchingURL = this.SEARCH_URI + this.ArtistName + AUTH_URI + env.get("GENIUS");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(fetchingURL)).build();

        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .join();

        JSONObject responseJson = new JSONObject(response).getJSONObject("response");
        this.ArtistData = responseJson;
    }

    private void cleanData() {
        JSONArray hits = this.ArtistData.getJSONArray("hits");
    }

    private void getArtistID() {
        JSONArray hits = this.ArtistData.getJSONArray("hits");
        this.ArtistID = hits.getJSONObject(0).getJSONObject("result").getJSONObject("primary_artist").getNumber("id")
                .toString();
        this.Songs = hits;

    }

    private void getAlternativeNames() {
        String fetchArtistInfo = this.ARTIST_URI + this.ArtistID + AUTH_URI + env.get("GENIUS");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().header("Authorization", "Bearer " + env.get("GENIUS"))
                .uri(URI.create(fetchArtistInfo)).build();
        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .join();

        JSONObject responseJson = new JSONObject(response).getJSONObject("response").getJSONObject("artist");
        this.AlterateNames = responseJson.getJSONArray("alternate_names");
    }

    private void concatenateJSON() {
        this.GeniusFinalObject = new JSONObject();
        this.GeniusFinalObject.put("artistID", this.ArtistID);
        this.GeniusFinalObject.put("songs", this.Songs);
        this.GeniusFinalObject.put("alternateNames", this.AlterateNames);

    }

    public JSONObject showArtistData() {
        this.fetchAristData();
        this.cleanData();
        this.getArtistID();
        this.getAlternativeNames();
        this.concatenateJSON();

        return this.GeniusFinalObject;
    }

}
