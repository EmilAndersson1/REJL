package services.spotify;

import controll.Controller;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.spotify.Playlist;
import services.APIService;

/**
 * https://developer.spotify.com/documentation/web-api/reference/playlists/create-playlist/
 */
public class PlaylistCreation extends APIService {

    public PlaylistCreation(Controller controller) {
        super(controller);
    }

    @Override
    public HttpResponse<JsonNode> jsonResponse(Controller controller) {

        String userId = controller.getUserId();
        String playlistname = controller.getWeather() + "-" + controller.getGenre();

        return Unirest.post("https://api.spotify.com/v1/users/{user_id}/playlists")
                .header("Authorization", "Bearer " + controller.getAuthorizationToken().accessToken)
                .header("Content-Type", "application/json")
                .routeParam("user_id", userId)
                .body("{ \"name\": \"" + playlistname + "\" }")
                .asJson();
    }

    @Override
    public Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject) {
        return gson.fromJson(jsonObject.toString(), Playlist.class);
    }
}
