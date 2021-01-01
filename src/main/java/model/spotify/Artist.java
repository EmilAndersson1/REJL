package model.spotify;

/**
 * https://developer.spotify.com/documentation/web-api/reference/object-model/#artist-object-simplified
 */
public class Artist {

    public Artist() {
    }

    public String name = "";

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                '}';
    }
}
