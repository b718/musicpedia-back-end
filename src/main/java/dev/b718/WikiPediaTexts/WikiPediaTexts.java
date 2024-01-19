package dev.b718.WikiPediaTexts;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/* 
! The goal of this is to get a bio of the artist from Wikipedia
 */
public class WikiPediaTexts {
    private String ArtistName;
    private final String URI_BASE = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&titles=";

    public WikiPediaTexts(String artistName) {
        this.ArtistName = artistName;
    }

    private void handleArtistName() {
        this.ArtistName = this.ArtistName.replace(" ", "_");
    }

    private String parseData(String responseBody) {
        JSONObject ArtistObject = new JSONObject(responseBody);
        Set<String> ArtistKeySet = ArtistObject.getJSONObject("query").getJSONObject("pages").keySet();

        String HtmlData = ArtistObject.getJSONObject("query").getJSONObject("pages")
                .getJSONObject(ArtistKeySet.iterator().next())
                .getString("extract");

        return HtmlData;
    }

    public static JSONObject convertHtmlToJson(String html) {
        Document doc = Jsoup.parse(html);
        JSONObject result = new JSONObject();
        JsonArray paragraphs = new JsonArray();

        Elements pTags = doc.select("p");
        for (Element pTag : pTags) {
            String text = pTag.text();
            if (text.length() > 0) {
                paragraphs.add(text);

            }
        }

        result.append("textOnArist", paragraphs);
        return result;
    }

    public JSONObject getInformationOnArtist() {
        this.handleArtistName();
        String apiUrl = URI_BASE + this.ArtistName;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();

        String result = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body).join();

        result = parseData(result);
        JSONObject WikipediaJSON = convertHtmlToJson(result);

        return WikipediaJSON;

    }
}
