//package services.spotify;
//
//import controll.Controller;
//import com.google.gson.Gson;
//import kong.unirest.HttpResponse;
//import kong.unirest.JsonNode;
//import kong.unirest.Unirest;
//import kong.unirest.json.JSONObject;
//import model.spotify.Code;
//import services.APIService;
//
///**
// * Service for getting user authorization to Spotify API.
// * User authentication is required to create a playlist.
// *
// * https://developer.spotify.com/documentation/general/guides/authorization-guide/#authorization-code-flow
// */
//public class UserLogin extends APIService {
//
//    public UserLogin(Controller controller) {
//        super(controller);
//    }
//
//    @Override
//    public HttpResponse<JsonNode> jsonResponse(Controller controller) {
//        return Unirest.get("https://accounts.spotify.com/authorize")
//                .queryString("redirect_uri", "http://localhost:8888/callback/")
//                .queryString("client_id", controller.getClientId())
//                .queryString("response_type", "code")
//                .queryString("scope", "playlist-modify-public")
//                .asJson();
//    }
//
//    @Override
//    public Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject) {
//        return gson.fromJson(jsonObject.toString(), Code.class);
//    }
//}
