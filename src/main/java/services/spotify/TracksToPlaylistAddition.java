package services.spotify;

import controll.Controller;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.spotify.Playlist;
import model.spotify.RecommendedTracks;
import model.spotify.Uris;
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
        RecommendedTracks recommendedTracks = controller.getRecommendedTracks();

        Uris trackUris = new Uris();
        trackUris.uris = new String[recommendedTracks.tracks.length];

        for (int i = 0; i < recommendedTracks.tracks.length; i++) {
            trackUris.uris[i] = recommendedTracks.tracks[i].uri;
        }
        String uris = new Gson().toJson(trackUris);

        System.out.println("" + uris);

        return Unirest.post("https://api.spotify.com/v1/playlists/{playlist_id}/tracks")
                .header("Authorization", "Bearer " + controller.getAuthorizationToken().accessToken)
                .header("Content-Type", "application/json")
                .routeParam("playlist_id", playlist.id)
                .body(uris)
//                .body("{\"uris\": " +
//                        "[" +
//                        "\"spotify:track:4iV5W9uYEdYUVa79Axb7Rh\"," +
//                        "\"spotify:track:1301WleyT98MSxVHPZCA6M\", " +
//                        "\"spotify:episode:512ojhOuo1ktJprKbVcKyQ\"" +
//                        "]" +
//                        "}")
                .asJson();
    }

    @Override
    public Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject) {
        return playlist;
    }
}
