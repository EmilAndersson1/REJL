//package services.spotify;
//
//import controll.Controller;
//import com.google.gson.Gson;
//import kong.unirest.HttpResponse;
//import kong.unirest.JsonNode;
//import kong.unirest.Unirest;
//import kong.unirest.json.JSONObject;
//import model.spotify.Playlist;
//import services.APIService;
//
///**
// * API service implemented to communicate with the Spotify API endpoint for tracks to playlist addition.
// * https://developer.spotify.com/documentation/web-api/reference/playlists/add-tracks-to-playlist/
// *
// * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
// */
//public class TracksToPlaylistAddition extends APIService {
//    private Playlist playlist;
//    public TracksToPlaylistAddition(Controller controller) {
//        super(controller);
//    }
//
//    /**
//     * To post some tracks to a specified playlist on Spotify.
//     * @param controller The server controller.
//     * @return A success response as json.
//     */
//    @Override
//    public HttpResponse<JsonNode> jsonResponse(Controller controller) {
//        playlist = controller.getPlaylist();
//        String uris = controller.getTracks();
//
//        return Unirest.post("https://api.spotify.com/v1/playlists/{playlist_id}/tracks")
//                .header("Authorization", "Bearer " + controller.getAuthorizationToken().accessToken)
//                .header("Content-Type", "application/json")
//                .routeParam("playlist_id", playlist.id)
//                .body(uris)
//                .asJson();
//    }
//
//    /**
//     * Return the java Playlist bean.
//     * @param gson The Gson object.
//     * @param jsonObject The Json object.
//     * @return The java Playlist bean.
//     */
//    @Override
//    public Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject) {
//        return playlist;
//    }
//}
