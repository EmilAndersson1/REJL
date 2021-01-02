package services.yr;

import controll.Controller;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import com.google.gson.Gson;
import model.yr.Weather;
import services.APIService;

/**
 * Service for communication with a Weather API: YR.
 * The retrieved JSON code is represented in a bean (java object) for easier manipulation in java.
 */
public class WeatherRetrieval extends APIService {

    public WeatherRetrieval(Controller controller) {
        super(controller);
    }

    @Override
    public HttpResponse<JsonNode> jsonResponse(Controller controller) {
        return Unirest.get("https://api.met.no/weatherapi/locationforecast/2.0/compact?")
                .header("Accept", "application/json")
                .queryString("lat", controller.getLatitude())
                .queryString("lon", controller.getLongitude())
                .asJson();
    }

    @Override
    public Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject) {
        JSONObject jsonFieldObject = jsonObject.getJSONObject("properties");
        return gson.fromJson(jsonFieldObject.toString(), Weather.class);
    }
}