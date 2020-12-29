package services;

import Controllers.DataHandler;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.Tracks;

/**
 * https://developer.spotify.com/documentation/web-api/reference-beta/#endpoint-get-recommendations
 */
public class SpotifyValanceSearchService extends APIService {

    public SpotifyValanceSearchService(DataHandler dataHandler) {
        super(dataHandler);
    }

    @Override
    public HttpResponse<JsonNode> response(DataHandler dataHandler) {
        float valence = dataHandler.getValance();
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
                .header("Authorization", "Bearer " + dataHandler.getAuthorizationToken().access_token)
                .queryString("limit", 10)
                // A comma separated list of Spotify IDs for seed artists.
                // Up to 5 seed values may be provided in any combination
                // of seed_artists, seed_tracks and seed_genres.
                .queryString("seed_artists", "6uothxMWeLWIhsGeF7cyo4") //dataHandler.getArtists()) //Enya hardcoded
//                .queryString("seed_genres", dataHandler.getGeres())
//                .queryString("seed_tracks", dataHandler.getTracks())
                .queryString("min_valence", minValence)
                .queryString("max_valence", maxValence)
                .asJson();
    }

    @Override
    public Object returnObject(Gson gson, JSONObject jsonObject) {
        return gson.fromJson(jsonObject.toString(), Tracks.class);
    }
}