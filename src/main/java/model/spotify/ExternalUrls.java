package model.spotify;

import com.google.gson.annotations.SerializedName;

/**
 * A bean holding a representation of url.
 * https://developer.spotify.com/documentation/web-api/reference/object-model/#external-url-object
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
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
