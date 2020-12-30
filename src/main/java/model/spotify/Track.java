package model.spotify;

public class Track {

    public Track() {
    }

    public String id            = "";

    public String name          = "";

    public String uri           = "";

    @Override
    public String toString() {
        return "Track{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
