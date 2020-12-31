package services.spotify;

import controll.Controller;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import services.APIService;

public class PlaylistCreation extends APIService {

    public PlaylistCreation(Controller controller) {
        super(controller);
    }

    @Override
    public HttpResponse<JsonNode> jsonResponse(Controller controller) {

        String hardCodedUserId = "1164261797";
        String hardCodedPlaylistName = "SkolProjekt";

        return Unirest.post("https://api.spotify.com/v1/users/{user_id}/playlists")
                .header("Authorization", "Bearer " + controller.getAuthorizationToken().accessToken)
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
