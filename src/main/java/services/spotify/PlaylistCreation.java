package services.spotify;

import controllers.DataHandler;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.spotify.Playlist;
import services.APIService;

public class PlaylistCreation extends APIService {

    public PlaylistCreation(DataHandler dataHandler) {
        super(dataHandler);
    }

    @Override
    public HttpResponse<JsonNode> jsonResponse(DataHandler dataHandler) {

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
    public Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject) {
        return gson.fromJson(jsonObject.toString(), model.spotify.Playlist.class);
    }
}
