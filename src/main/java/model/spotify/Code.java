package model.spotify;

/**
 * https://developer.spotify.com/documentation/general/guides/authorization-guide/#authorization-code-flow
 */
public class Code {

    public Code() {
    }

    public String code = "";
    public String error = "no error";
    public String state = "";

    @Override
    public String toString() {
        return "Code{" +
                "code='" + code + '\'' +
                ", error='" + error + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
