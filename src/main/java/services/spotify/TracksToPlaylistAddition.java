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
 * https://developer.spotify.com/documentation/web-api/reference/playlists/add-tracks-to-playlist/
 */
public class TracksToPlaylistAddition extends APIService {
    public TracksToPlaylistAddition(Controller controller) {
        super(controller);
    }

    private Playlist playlist;

    @Override
    public HttpResponse<JsonNode> jsonResponse(Controller controller) {

        playlist = controller.getPlaylist();
        String uris = controller.getTracks();

        return Unirest.post("https://api.spotify.com/v1/playlists/{playlist_id}/tracks")
                .header("Authorization", "Bearer " + controller.getAuthorizationToken().accessToken)
                .header("Content-Type", "application/json")
                .routeParam("playlist_id", playlist.id)
                .body(uris)
                .asJson();
    }

    @Override
    public Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject) {
        return playlist;
    }
}
