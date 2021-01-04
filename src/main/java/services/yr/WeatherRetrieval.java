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
 * Service for communication with a Weather API: YR.
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
        if (symbolCode.contains("_")) {
            symbolCode = symbolCode.split("_")[0];
        }
        JSONObject jsonWeather = new JSONObject();
        jsonWeather.put("air_temperature", temperature).put("symbol_code", symbolCode);
        return gson.fromJson(jsonWeather.toString(), Weather.class);
    }

    private JSONObject getTimeSeriesNow(JSONArray jsonTimeSeries) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime timeNow = LocalDateTime.now();
        timeNow.format(formatter);
        for (int i = 0; i < jsonTimeSeries.length(); i++) {
            String jsonTimeStamp = jsonTimeSeries.getJSONObject(i).getString("time");
            LocalDateTime timeStamp = LocalDateTime.parse(jsonTimeStamp, formatter);
            if (timeStamp.isAfter(timeNow)) {
                return jsonTimeSeries.getJSONObject(i-1);
            }
        }
        return jsonTimeSeries.getJSONObject(0); // Latest cached weather hour from Yr. If loop fails.
    }
}