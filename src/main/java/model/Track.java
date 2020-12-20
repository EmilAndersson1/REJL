package model;

public class Track {

    public Track() {
    }

    public String name          = "";

    public String preview_url   = "";

    @Override
    public String toString() {
        return "Track{" +
                "name='" + name + '\'' +
                ", preview_url='" + preview_url + '\'' +
                '}';
    }
}
