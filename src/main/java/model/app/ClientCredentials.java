package model.app;

/**
 * Client credentials.
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
 */
public class ClientCredentials {

    public String getClientID() {
        return "444dff56b04044f3b091504c069e9954";
    }

    public String getClientSecret() {
        return "a15ce62d40ba4a0697be814f59602ebc";
    }

    public String getAuthorizationUrl() {
        return "https://accounts.spotify.com/sv/authorize" +
                "?client_id=" +     "444dff56b04044f3b091504c069e9954" +
                "&response_type=" + "code" +
                "&redirect_uri=" +  "http:%2F%2Flocalhost:8888%2Fcallback%2F" +
                "&scope=" +         "playlist-modify-public";
    }
}
