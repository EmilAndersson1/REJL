package model.spotify;

import com.google.gson.annotations.SerializedName;

/**
 * We might need a java bean for the Spotify handling.
 */
public class Token {

    public Token() {
    }

    @SerializedName("access_token")
    public String accessToken = "";

    @SerializedName("token_type")
    public String tokenType = "";

    @SerializedName("expires_in")
    public int expiresIn;

    public String scope        = "";

    @SerializedName("refresh_token")
    public String refreshToken = "";

    @Override
    public String toString() {
        return "Token{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", scope='" + scope + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
