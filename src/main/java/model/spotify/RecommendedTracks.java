package model.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * https://developer.spotify.com/documentation/web-api/reference-beta/#endpoint-get-recommendations
 */
public class RecommendedTracks {

    public RecommendedTracks() {
    }

    @SerializedName("tracks")
    public Track[] tracks = null;

    @Override
    public String toString() {
        return "RecommendedTracks{" +
                "tracks=" + Arrays.toString(tracks) +
                '}';
    }
}
