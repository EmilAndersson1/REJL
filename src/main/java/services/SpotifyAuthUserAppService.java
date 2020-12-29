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
    public Object returnObject(Gson gson, JSONObject jsonObject) {
        return gson.fromJson(jsonObject.toString(), Token.class);
    }
}
