package model;

public class Track {

    public Track() {
    }

    public String id            = "";

    public String name          = "";

    @Override
    public String toString() {
        return "Track{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
