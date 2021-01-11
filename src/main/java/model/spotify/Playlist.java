package model.spotify;

import com.google.gson.annotations.SerializedName;

/**
 * A bean representing a playlist.
 * https://developer.spotify.com/documentation/web-api/reference/object-model/#playlist-object-full
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
 */
public class Playlist {

    public Playlist() {
    }

    public String id = "";
    public String name = "";
    @SerializedName("external_urls")
    public ExternalUrls externalUrls = null;
    public String uri = "";

    @Override
    public String toString() {
        return "Playlist{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", externalUrls=" + externalUrls +
                ", uri='" + uri + '\'' +
                '}';
    }
}
