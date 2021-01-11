package services.yr;

import controll.Controller;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import com.google.gson.Gson;
import model.yr.Weather;
import services.APIService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * API service implemented for communication with a Weather API: YR, to get a weather response.
 * https://api.met.no/weatherapi/documentation
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
 */
public class WeatherRetrieval extends APIService {

    public WeatherRetrieval(Controller controller) {
        super(controller);
    }

    /**
     * To get a weather response from YR.
     * @param controller The server controller.
     * @return The response as json.
     */
    @Override
    public HttpResponse<JsonNode> jsonResponse(Controller controller) {
        return Unirest.get("https://api.met.no/weatherapi/locationforecast/2.0/compact?")
                .header("Accept", "application/json")
                .queryString("lat", controller.getLatitude())
                .queryString("lon", controller.getLongitude())
                .asJson();
    }

    /**
     * Pick out the wanted fields from the json object and deserialize by marshalling to return a java Weather bean.
     * @param gson The Gson object.
     * @param jsonObject The Json object.
     * @return The Weather bean.
     */
    @Override
    public Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONObject("properties").getJSONArray("timeseries");
        JSONObject jsonObjectData = getTimeSeriesNow(jsonArray).getJSONObject("data");
        float temperature = jsonObjectData
                .getJSONObject("instant")
                .getJSONObject("details")
                .getFloat("air_temperature");
        String symbolCode = jsonObjectData
                .getJSONObject("next_1_hours")
                .getJSONObject("summary")
                .getString("symbol_code");
        JSONObject jsonWeather = new JSONObject();
        jsonWeather.put("air_temperature", temperature).put("symbol_code", symbolCode);
        return gson.fromJson(jsonWeather.toString(), Weather.class);
    }

    /**
     * The weather forecast from YR is cached. This helper method finds the current, ongoing, hour in a
     * time series of hourly forecast points.
     * @param jsonTimeSeries The forecast time series.
     * @return The forecast for the current hour right now.
     */
    private JSONObject getTimeSeriesNow(JSONArray jsonTimeSeries) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime timeNow = LocalDateTime.now();
        timeNow.format(formatter);
        for (int i = 0; i < jsonTimeSeries.length(); i++) {
            String jsonTimeStamp = jsonTimeSeries.getJSONObject(i).getString("time"); // Forecast hour to check.
            LocalDateTime timeStamp = LocalDateTime.parse(jsonTimeStamp, formatter); // Same format.
            if (timeStamp.isAfter(timeNow)) {
                return jsonTimeSeries.getJSONObject(i-1); // First time of measure is always before now.
            }
        }
        return jsonTimeSeries.getJSONObject(0); // If loop fails, use the first hour from time of measure weather.
    }
}