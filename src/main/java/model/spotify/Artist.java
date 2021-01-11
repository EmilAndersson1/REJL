package model.spotify;

/**
 * A bean representing an artist.
 * https://developer.spotify.com/documentation/web-api/reference/object-model/#artist-object-simplified
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
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
