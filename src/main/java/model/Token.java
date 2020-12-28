package model;

/**
 * We might need a java bean for the Spotify handling.
 */
public class Token {

    public Token() {
    }

    public String access_token = "";

    public String token_type   = "";

    public int expires_in;

    public String scope        = "";

    public String refresh_token = "";

    @Override
    public String toString() {
        return "Token{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in=" + expires_in +
                ", scope='" + scope + '\'' +
                '}';
    }
}
