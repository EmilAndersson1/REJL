package model.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * A bean representing some tracks.
 * https://developer.spotify.com/documentation/web-api/reference-beta/#endpoint-get-recommendations
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
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
