package model;

import java.util.Arrays;

public class Playlist {

    public Playlist() {
    }

    public Track[] tracks = null;

    @Override
    public String toString() {
        return "Playlist{" +
                "tracks=" + Arrays.toString(tracks) +
                '}';
    }
}
