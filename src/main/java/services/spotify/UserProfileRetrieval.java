package services.spotify;

import com.google.gson.Gson;
import controll.Controller;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.spotify.UserProfile;
import services.APIService;

/**
 * API service implemented to communicate with the Spotify API endpoint for user profile retrieval.
 * https://developer.spotify.com/documentation/web-api/reference/users-profile/get-current-users-profile/
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
 */
public class UserProfileRetrieval extends APIService {
    public UserProfileRetrieval(Controller controller) {
        super(controller);
    }

    /**
     * To get a user profile response from Spotify.
     * @param controller The server controller.
     * @return The response as json.
     */
    @Override
    public HttpResponse<JsonNode> jsonResponse(Controller controller) {
        return Unirest.get("https://api.spotify.com/v1/me")
                .header("Authorization", "Bearer " + controller.getAuthorizationToken().accessToken)
                .asJson();
    }

    /**
     * Deserialize by marshalling to return a java UserProfile bean.
     * @param gson The Gson object.
     * @param jsonObject The Json object.
     * @return The UserProfile bean.
     */
    @Override
    public Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject) {
        return gson.fromJson(jsonObject.toString(), UserProfile.class);
    }
}
