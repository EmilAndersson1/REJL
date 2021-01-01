package model.spotify;

import com.google.gson.annotations.SerializedName;

/**
 * https://developer.spotify.com/documentation/web-api/reference/object-model/#external-url-object
 */
public class ExternalUrls {
    public ExternalUrls() {
    }

    @SerializedName("spotify")
    public String spotifyUrl = "";

    @Override
    public String toString() {
        return "Urls{" +
                "spotifyUrl='" + spotifyUrl + '\'' +
                '}';
    }
}
