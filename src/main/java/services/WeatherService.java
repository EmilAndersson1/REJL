package services;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.CurrentWeather;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Service for communication with a Weather API. Currently: YR API
 *
 * The retrieved JSON code is to be represented in a java object for easier manipulation in java.
 *
 */
public class WeatherService {

    /*
    Hardcoded main method for TESTING.
    The getWeather method is supposed to be called from a control class.
    TODO: Remove main method.
     */
    public static void main(String[] args) {
        new WeatherService().getWeather("-16.516667", "-68.166667");
    }

    /**
     *  Retrieve some information about the weather based on a location.
     *
     * @param latitud Latitude coordinate.
     * @param longitud Longitude coordinate.
     */
    public Object getWeather(String latitud, String longitud) {

        Object weather = null;

        // Base url of weather API.
        String url = "https://api.met.no/weatherapi/locationforecast/2.0/compact?";

        // Use Unirest library to get information from API.
        HttpResponse<JsonNode> response = Unirest.get(url)
                .header("Accept", "application/json")
                .queryString("lat", latitud)                // Build on base url
                .queryString("lon", longitud)               // Build on base url
                .asJson();

        // Retrieve the parsed JSONObject from the response.
        JsonNode jsonNode       = response.getBody();
        JSONObject jsonObject   = jsonNode.getObject();

        // Gson instance for marshalling and unmarshalling.
        GsonBuilder builder = new GsonBuilder();
        Gson gson           = builder.create();

        weather = returnAndPrintWeather(jsonObject, gson); //TODO: Swap for some more valuable code.

        // Close Unirest connection.
        Unirest.shutDown();

        return weather;
    }

    //TODO: This is good for retrieving a decent weather parameter.
    // Maybe have to make a search to correct time forecast*.
    private CurrentWeather returnAndPrintWeather(JSONObject jsonObject, Gson gson) {
//        System.out.println(jsonObject.toString(1));

        // Retrieve wanted fields from the JSONObject.
        JSONObject jsonField = jsonObject
                .getJSONObject("properties")
                .getJSONArray("timeseries")
                .getJSONObject(0)   //TODO: *Sök upp en tid som matchar. YR använder cachade rapporter, ej liveuppdatering.
                .getJSONObject("data")
                .getJSONObject("next_1_hours")
                .getJSONObject("summary");
        String json = jsonField.toString();

        System.out.println("\nTEST WEATHER from json to java:");
        CurrentWeather currentWeather = gson.fromJson(json, CurrentWeather.class); //Unmarshalling: JSON --> java Object
        System.out.println(currentWeather.symbol_code);

        System.out.println("\nTEST WEATHER back to json from java:");
        String javaBackToJson = gson.toJson(currentWeather); //Marshalling: java Object --> JSON
        System.out.println(javaBackToJson);

        return currentWeather;
    }

//    //TODO: Remove this method.
//    private Location testAndPrintMarshalling(JSONObject jsonObject, Gson gson) {
//
//        // Retrieve wanted fields from the JSONObject.
//        JSONObject jsonField    = jsonObject.getJSONObject("geometry");
//        String json             = jsonField.toString();
//
//        System.out.println("\nTEST unmarshalling from json to java:");
//        // Unmarshalling: JSON --> java Object
//        Location location = gson.fromJson(json, Location.class);
//        try {
////            System.out.println(location.type); // The unmarshalling workes fine anyway - ignores fields that is not present in the java objekt.
//            System.out.println(Arrays.toString(location.coordinates));
//            System.out.println("SUCCESS!");
//        } catch (Exception e) {
//            System.out.println("Något problem vid unmarshalling!");
//            e.printStackTrace();
//        }
//
//        System.out.println("\nTEST marshalling back to json from java:");
//        // Marshalling: java Object --> JSON
//        String javaBackToJson = gson.toJson(location);
//        try {
//            System.out.println(javaBackToJson);
//            System.out.println("SUCCESS!");
//        } catch (Exception e) {
//            System.out.println("Något problem vid marshalling!");
//            e.printStackTrace();
//        }
//
//        return location;
//    }
}