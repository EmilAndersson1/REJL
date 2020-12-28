package services;

import Controllers.DataHandler;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.Code;
import model.Token;

/**
 * Service for getting user authorization to Spotify API.
 * User authentication is required to create a playlist.
 */
public class SpotifyAuthUserService extends APIService {

    public SpotifyAuthUserService(DataHandler dataHandler) {
        super(dataHandler);
    }

    @Override
    public HttpResponse<JsonNode> response(DataHandler dataHandler) {
        return Unirest.get("https://accounts.spotify.com/authorize")
                .queryString("client_id", dataHandler.getClientId())
                .queryString("response_type", "code")
                .queryString("redirect_uri", "http://localhost:8888/callback/")
                .queryString("scope", "playlist-modify-public")
                .asJson();
    }

    @Override
    public Object returnObject(Gson gson, JSONObject jsonObject) {
        return gson.fromJson(jsonObject.toString(), Code.class);
    }
}
