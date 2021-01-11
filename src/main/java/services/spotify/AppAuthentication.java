package services.spotify;

import controll.Controller;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.spotify.Token;
import services.APIService;

/**
 * API service implemented to communicate with the Spotify API endpoint for getting authorization to Spotify API
 * endpoints with user data.
 * https://developer.spotify.com/documentation/general/guides/authorization-guide/#authorization-code-flow
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
 */
public class AppAuthentication extends APIService {
    public AppAuthentication(Controller controller) {
        super(controller);
    }

    /**
     * To post authorization for a user and receive a response containing a token from Spotify.
     * @param controller The server controller.
     * @return The json response.
     */
    @Override
    public HttpResponse<JsonNode> jsonResponse(Controller controller) {
        String encoded = controller.getEncodedClientCredentials();
        String code = controller.getAuthorizationCode();
        return Unirest.post("https://accounts.spotify.com/api/token")
                .header("Authorization", "Basic " + encoded)
                .field("grant_type", "authorization_code")
                .field("code", code)
                .field("redirect_uri", "http://localhost:8888/callback/")
                .asJson();
    }

    /**
     * Deserialize by marshalling to return a java Token bean.
     * @param gson The Gson object.
     * @param jsonObject The Json object.
     * @return The Token bean.
     */
    @Override
    public Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject) {
        return gson.fromJson(jsonObject.toString(), Token.class);
    }
}
