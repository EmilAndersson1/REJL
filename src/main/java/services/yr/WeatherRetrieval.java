package services.yr;

import controll.Controller;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.yr.CurrentWeather;

import com.google.gson.Gson;
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
        // Retrieve wanted fields from the JSONObject. Parsing the JSON tree.
        JSONObject jsonFieldObject = jsonObject
                .getJSONObject("properties")
                .getJSONArray("timeseries")
                .getJSONObject(0)   //TODO: Sök upp en tid som matchar. YR använder cachade rapporter, ej liveuppdatering.
                .getJSONObject("data")
                .getJSONObject("next_1_hours")
                .getJSONObject("summary");

        return gson.fromJson(jsonFieldObject.toString(), CurrentWeather.class);
    }
}