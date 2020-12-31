package model.spotify;

import com.google.gson.annotations.SerializedName;

public class Playlist {

    public Playlist() {
    }

    @SerializedName("external_urls")
    public ExternalUrls externalUrls;

    public String id = "";

    public String name = "";


    private class ExternalUrls {
        public ExternalUrls() {
        }

        @SerializedName("spotify")
        public String spotifyUrl = "";
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "externalUrls=" + externalUrls +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
