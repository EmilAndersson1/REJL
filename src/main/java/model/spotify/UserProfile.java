package model.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * A bean representing a user profile.
 * https://developer.spotify.com/documentation/web-api/reference/object-model/#user-object-private
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
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
