package services.spotify;

import controllers.DataHandler;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.spotify.Playlist;
import model.spotify.Tracks;
import model.spotify.Uris;
import services.APIService;

public class AddTracksToPlaylist extends APIService {
    public AddTracksToPlaylist(DataHandler dataHandler) {
        super(dataHandler);
    }

    private Playlist playlist;
    private Uris trackUris;

    @Override
    public HttpResponse<JsonNode> response(DataHandler dataHandler) {

        playlist = dataHandler.getPlaylist();

        Tracks tracks = dataHandler.getTracks();

        trackUris = new Uris();
        trackUris.uris = new String[tracks.tracks.length];

        for (int i = 0; i < tracks.tracks.length; i++) {
            trackUris.uris[i] = tracks.tracks[i].uri;
        }
        String uris = new Gson().toJson(trackUris);

        System.out.println(uris);

        return Unirest.post("https://api.spotify.com/v1/playlists/{playlist_id}/tracks")
                .header("Authorization", "Bearer " + dataHandler.getAuthorizationToken().access_token)
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
    public Object returnObject(Gson gson, JSONObject jsonObject) {
        return playlist;
    }
}
