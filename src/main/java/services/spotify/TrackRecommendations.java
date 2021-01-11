package services.spotify;

import controll.Controller;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.spotify.RecommendedTracks;
import model.spotify.Track;
import services.APIService;

/**
 * API service implemented to communicate with the Spotify API endpoint for track recommendations retrieval.
 * https://developer.spotify.com/documentation/web-api/reference-beta/#endpoint-get-recommendations
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
 */
public class TrackRecommendations extends APIService {
    Controller controller;
    public TrackRecommendations(Controller controller) {
        super(controller);
        this.controller = controller;
    }

    /**
     * To get a tracks recommendations response from Spotify, based on a valence interval and a genre.
     * @param controller The server controller.
     * @return The response as json.
     */
    @Override
    public HttpResponse<JsonNode> jsonResponse(Controller controller) {
        // Calculate a valence interval.
        float valence = controller.getValance();
        float interval = 0.200f;
        float minValence = valence - interval/2;
        float maxValence = minValence + interval;

        return Unirest.get("https://api.spotify.com/v1/recommendations")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + controller.getAuthorizationToken().accessToken)
                .queryString("limit", 10)
                .queryString("seed_genres", controller.getGenre())
                .queryString("min_valence", minValence)
                .queryString("max_valence", maxValence)
                .asJson();
    }

    /**
     * Deserialize by marshalling to return a java RecommendedTracks bean and add an album image for each track.
     * @param gson The Gson object.
     * @param jsonObject The Json object.
     * @return The RecommendedTracks bean.
     */
    @Override
    public Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject) {
        RecommendedTracks recommendedTracks = gson.fromJson(jsonObject.toString(), RecommendedTracks.class);

        for (Track track : recommendedTracks.tracks) {
            String trackId = track.id;
            track.imageUrl = getTrackImageAtSpotify(trackId); // Add an image to each track.
        }

        return recommendedTracks;
    }

    /**
     * To get an album image for a track from a API response from the Spotify tracks endpoint.
     * @param trackId the tracks Spotify id.
     * @return The image Spotify url.
     */
    private String getTrackImageAtSpotify(String trackId) {
        HttpResponse<JsonNode> response = Unirest.get("https://api.spotify.com/v1/tracks/{id}")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + controller.getAuthorizationToken().accessToken)
                .routeParam("id", trackId)
                .asJson();
        String imageUrl = response.getBody().getObject()
                .getJSONObject("album")
                .getJSONArray("images")
                .getJSONObject(1)
                .getString("url");
        return imageUrl;
    }
}