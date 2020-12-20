package services;

import Controllers.DataHandler;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.Track;

/**
 * Service for getting a track from Spotify API.
 * The retrieved JSON code is represented in a bean (java object) for easier manipulation in java.
 */
public class SpotifyTrackService extends APIService {

    public SpotifyTrackService(DataHandler dataHandler) {
        super(dataHandler);
    }

    @Override
    public HttpResponse<JsonNode> response(DataHandler dataHandler) {
        return Unirest.get("https://api.spotify.com/v1/tracks/{id}")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + dataHandler.getAuthorizationToken().access_token)
                .routeParam("id", dataHandler.getRequestTrack())
                .asJson();
    }

    @Override
    public Object returnObject(Gson gson, JSONObject jsonObject) {
        return gson.fromJson(jsonObject.toString(), Track.class);
    }
}
