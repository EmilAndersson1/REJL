package services.spotify;

import controllers.DataHandler;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.spotify.Token;
import services.APIService;

/**
 * Service for getting authorization to Spotify API.
 * The retrieved JSON code is represented in a bean (java object) for easier manipulation in java.
 */
public class AppAuthentication extends APIService {

    public AppAuthentication(DataHandler dataHandler) {
        super(dataHandler);
    }

    @Override
    public HttpResponse<JsonNode> jsonResponse(DataHandler dataHandler) {
        String encoded = dataHandler.getEncodedClientCredentials();
        String code = dataHandler.getCode();
        return Unirest.post("https://accounts.spotify.com/api/token")
                .header("Authorization", "Basic " + encoded)
                .field("grant_type", "authorization_code")
                .field("code", code)
                .field("redirect_uri", "http://localhost:8888/callback/")
                .asJson();
    }

    @Override
    public Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject) {
        return gson.fromJson(jsonObject.toString(), Token.class);
    }
}
