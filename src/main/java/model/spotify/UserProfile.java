package model.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * https://developer.spotify.com/documentation/web-api/reference/object-model/#user-object-private
 */
public class UserProfile {
    public UserProfile() {
    }

    @SerializedName("id")
    public String userId = "";
    @SerializedName("display_name")
    public String userName = "";
    @SerializedName("external_urls")
    public ExternalUrls externalUrls = null;
    public Image[] images = null;

    @Override
    public String toString() {
        return "UserProfile{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", externalUrls=" + externalUrls +
                ", images=" + Arrays.toString(images) +
                '}';
    }
}
