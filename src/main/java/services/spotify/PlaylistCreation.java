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
 * API service implemented to communicate with the Spotify API endpoint for playlist creation.
 * https://developer.spotify.com/documentation/web-api/reference/playlists/create-playlist/
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
 */
public class PlaylistCreation extends APIService {
    public PlaylistCreation(Controller controller) {
        super(controller);
    }

    /**
     * To post a playlist a users Spotify account.
     * @param controller The server controller.
     * @return The json response.
     */
    @Override
    public HttpResponse<JsonNode> jsonResponse(Controller controller) {

        String userId = controller.getUserId();

        return Unirest.post("https://api.spotify.com/v1/users/{user_id}/playlists")
                .header("Authorization", "Bearer " + controller.getAuthorizationToken().accessToken)
                .header("Content-Type", "application/json")
                .routeParam("user_id", userId)
                .body("{ \"name\": \"" + "My REJL Playlist" + "\" }")
                .asJson();
    }

    /**
     * Deserialize by marshalling to return a java Playlist bean.
     * @param gson The Gson object.
     * @param jsonObject The Json object.
     * @return The Playlist bean.
     */
    @Override
    public Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject) {
        return gson.fromJson(jsonObject.toString(), Playlist.class);
    }
}
