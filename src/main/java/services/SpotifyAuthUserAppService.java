package services;

import Controllers.DataHandler;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.Token;

/**
 * Service for getting authorization to Spotify API.
 * The retrieved JSON code is represented in a bean (java object) for easier manipulation in java.
 */
public class SpotifyAuthUserAppService extends APIService{

    public SpotifyAuthUserAppService(DataHandler dataHandler) {
        super(dataHandler);
    }

    @Override
    public HttpResponse<JsonNode> response(DataHandler dataHandler) {
        return Unirest.post("https://accounts.spotify.com/api/token")
                .header("Authorization", "Basic " + dataHandler.getEncodedClientCredentials())
                .field("grant_type", "cauthorization_code")
                .field("code", dataHandler.getAuthorizationCode().code)
                .field("redirect_uri", "http://localhost:8888/callback/")
                .asJson();
    }

    @Override
    public Object returnObject(Gson gson, JSONObject jsonObject) {
        return gson.fromJson(jsonObject.toString(), Token.class);
    }
}
