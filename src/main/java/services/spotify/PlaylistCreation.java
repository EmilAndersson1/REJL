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
 * https://developer.spotify.com/documentation/web-api/reference/playlists/add-tracks-to-playlist/
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
 */
public class PlaylistCreation extends APIService {
    public PlaylistCreation(Controller controller) {
        super(controller);
    }

    /**
     * To post a playlist a users Spotify account, and post some tracks to the specified playlist on Spotify.
     *
     * @param controller The server controller.
     * @return The json response.
     */
    @Override
    public HttpResponse<JsonNode> jsonResponse(Controller controller) {
        String userId = controller.getUserId();
        String uris = controller.getTracks();

        HttpResponse<JsonNode> response =
                Unirest.post("https://api.spotify.com/v1/users/{user_id}/playlists")
                        .header("Authorization", "Bearer " + controller.getAuthorizationToken().accessToken)
                        .header("Content-Type", "application/json")
                        .routeParam("user_id", userId)
                        .body("{ \"name\": \"" + "My REJL Playlist" + "\" }")
                        .asJson();
        JSONObject jsonObject = response.getBody().getObject();
        Playlist playlist = new Gson().fromJson(jsonObject.toString(), Playlist.class);

        String playlistId = playlist.id;

        Unirest.post("https://api.spotify.com/v1/playlists/{playlist_id}/tracks")
                .header("Authorization", "Bearer " + controller.getAuthorizationToken().accessToken)
                .header("Content-Type", "application/json")
                .routeParam("playlist_id", playlistId)
                .body(uris)
                .asJson();

        return Unirest.get("https://api.spotify.com/v1/playlists/{playlist_id}")
                .header("Authorization", "Bearer " + controller.getAuthorizationToken().accessToken)
                .header("Content-Type", "application/json")
                .routeParam("playlist_id", playlistId)
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
