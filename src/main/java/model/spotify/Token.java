package model.spotify;

import com.google.gson.annotations.SerializedName;

/**
 * A bean representing a token.
 * https://developer.spotify.com/documentation/general/guides/authorization-guide/#authorization-code-flow
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
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
