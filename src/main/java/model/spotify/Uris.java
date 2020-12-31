package model.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Uris {

    public Uris() {
    }

    @SerializedName("uris")
    public String[] uris = null;

    @Override
    public String toString() {
        return "Uris{" +
                "uris=" + Arrays.toString(uris) +
                '}';
    }
}
