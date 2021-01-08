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
 * https://developer.spotify.com/documentation/web-api/reference-beta/#endpoint-get-recommendations
 */
public class TrackRecommendations extends APIService {

    Controller controller;

    public TrackRecommendations(Controller controller) {
        super(controller);
        this.controller = controller;
    }

    @Override
    public HttpResponse<JsonNode> jsonResponse(Controller controller) {
        float valence = controller.getValance();
        float interval = 0.200f;
        float minValence = valence - interval/2;
        float maxValence = minValence + interval;
        try {
            assert (minValence>=0.000f) && (maxValence<=1.000f);
        } catch (Exception e) {
            System.out.println("Valence is broken...");
            e.printStackTrace();
        }
        System.out.println("TEST Valance: " + valence);
        System.out.println("TEST Min Valance: " + minValence);
        System.out.println("TEST Max Valance: " + maxValence);
        return Unirest.get("https://api.spotify.com/v1/recommendations")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + controller.getAuthorizationToken().accessToken)
                .queryString("limit", 10)
                .queryString("seed_genres", controller.getGenre())
                .queryString("min_valence", minValence)
                .queryString("max_valence", maxValence)
                .asJson();
    }

    @Override
    public Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject) {
        RecommendedTracks tracks = gson.fromJson(jsonObject.toString(), RecommendedTracks.class);
        for (Track track : tracks.tracks) {
            String trackId = track.id;
            track.imageUrl = getTrackImageAtSpotify(trackId);
        }
        return tracks;
    }

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