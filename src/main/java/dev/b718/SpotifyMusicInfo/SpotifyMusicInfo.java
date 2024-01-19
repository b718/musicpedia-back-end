package dev.b718.SpotifyMusicInfo;

import java.util.Map;

import org.json.JSONObject;

public class SpotifyMusicInfo {
    private Map<String, String> env;
    private String ArtistName;
    private JSONObject SpotifyFinalObject;

    public SpotifyMusicInfo(String artistName) {
        this.ArtistName = artistName;
        env = System.getenv();
    }
}
