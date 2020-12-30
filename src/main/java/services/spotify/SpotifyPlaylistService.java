package services.spotify;

import controllers.DataHandler;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.spotify.Playlist;
import services.APIService;

public class SpotifyPlaylistService extends APIService {

    public SpotifyPlaylistService(DataHandler dataHandler) {
        super(dataHandler);
    }

    @Override
    public HttpResponse<JsonNode> response(DataHandler dataHandler) {

        String hardCodedUserId = "1164261797";
        String hardCodedPlaylistName = "SkolProjekt";

        return Unirest.post("https://api.spotify.com/v1/users/{user_id}/playlists")
                .header("Authorization", "Bearer " + dataHandler.getAuthorizationToken().access_token)
                .header("Content-Type", "application/json")
                .routeParam("user_id", hardCodedUserId)
                .body("{ \"name\": \"" + hardCodedPlaylistName + "\" }")
                .asJson();
    }

    @Override
    public Object returnObject(Gson gson, JSONObject jsonObject) {
        return gson.fromJson(jsonObject.toString(), Playlist.class);
    }
}
