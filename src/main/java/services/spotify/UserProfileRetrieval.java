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
 * https://developer.spotify.com/documentation/web-api/reference/users-profile/get-current-users-profile/
 */
public class UserProfileRetrieval extends APIService {
    public UserProfileRetrieval(Controller controller) {
        super(controller);
    }

    @Override
    public HttpResponse<JsonNode> jsonResponse(Controller controller) {
        return Unirest.get("https://api.spotify.com/v1/me")
                .header("Authorization", "Bearer " + controller.getAuthorizationToken().accessToken)
                .asJson();
    }

    @Override
    public Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject) {
        return gson.fromJson(jsonObject.toString(), UserProfile.class);
    }
}
