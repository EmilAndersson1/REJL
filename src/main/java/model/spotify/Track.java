package model.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * A bean representing a track.
 * https://developer.spotify.com/documentation/web-api/reference/object-model/#track-object-full
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
 */
public class Track {

    public Track() {
    }

    public String id                    = "";
    public String name                  = "";
    @SerializedName("external_urls")
    public ExternalUrls externalUrls    = null;
    public String uri                   = "";
    public Artist[] artists             = null;
    public String imageUrl              = "";

    @Override
    public String toString() {
        return "Track{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", externalUrls=" + externalUrls +
                ", uri='" + uri + '\'' +
                ", artists=" + Arrays.toString(artists) +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
