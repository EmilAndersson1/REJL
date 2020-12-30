package model.spotify;

import java.util.Arrays;

public class Tracks {

    public Tracks() {
    }

    public Track[] tracks = null;

    @Override
    public String toString() {
        return "Tracks{" +
                "tracks=" + Arrays.toString(tracks) +
                '}';
    }
}
