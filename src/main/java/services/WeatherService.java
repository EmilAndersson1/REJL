package services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.Weather;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Service for communication with Spotify API.
 *
 */
public class WeatherService {

    public static void main(String[] args) {
        getWeather();
    }

    /**
     *  1. A small program that retrieves information about today from an external API
     *
     *  2. and presents this info in a more readable way as a console print out.
     *
     * The main method that's used to run the program.
     *
     */
    public static void getWeather() {

        Gson gson = new Gson();

        HttpResponse<JsonNode> response = Unirest.get("https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=60.10&lon=9.58")
                .header("Accept", "application/json")
                .asJson();
        JSONObject json = response.getBody().getObject();
        Weather weather = gson.fromJson(json, Weather.class);
//        System.out.println(response.getBody().getObject().toString());
        System.out.println(weather);
//        JsonNode json = response.getBody();
//        JSONObject weather = json.getObject();
//        System.out.println(weather.toString());
        Unirest.shutDown();

        //        HttpResponse<JsonNode> response;
//        response = Unirest.get("https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=60.10&lon=9.58")
//                .queryString("format", "json") // Unirest bygger helst sin egen query string
//                .asJson(); // Berätta för Unirest att vi får tillbaka JSON.

////        DateTime today = new DateTime();
////        String baseUrl = "http://api.dryg.net/dagar/v2/";
//        String baseUrl = "https://api.met.no/weatherapi/locationforecast/2.0/compact?"; //lat=-16.516667&lon=-68.166667&altitude=4150
//        String url = null;
//
//        HttpClient httpclient = null;
//        HttpGet httpGet = null;
//        HttpResponse response = null;
//        StatusLine status = null;
//        HttpEntity entity = null;
//        InputStream data = null;
//        Reader reader = null;
//
//        GsonBuilder builder = new GsonBuilder();
//        Gson json = builder.create();
//
////        EnvelopeBean envelope = null;
//        Weather weather = null;
//
//        url = baseUrl + "lat=60.10&lon=9.58"; // Build a url.
//
//        try {
//            // Create the client that will call the API
//            httpclient = HttpClients.createDefault();
//            httpGet = new HttpGet(url);
//
//            // Call the API and verify that all went well
//            response = httpclient.execute(httpGet);
//            status = response.getStatusLine();
//
//            if (status.getStatusCode() == 200) {
//
//                // All went well. Let's fetch the data
//                entity = response.getEntity();
//                data = entity.getContent();
//
//                try {
//                    // Attempt to parse the data as JSON
//                    reader = new InputStreamReader(data);
//                    json = builder.create();
//                    weather = json.fromJson(reader, Weather.class);
//
//                    // Yep, that went well. Let's print today's information.
//                    // As the API will return a list of days, we'll need to
//                    // fetch "today", which will be the first and only object.
//                    for (Map.Entry<String, DayBean> entry :
//                            envelope.days.entrySet()) {
//                        printDay(entry.getValue());
//                    }
//
//                } catch (Exception e) {
//                    // Something didn't went well. No calls for us.
//                    e.printStackTrace();
//                    System.out.println("Det blev fel. Gå hem och sov.");
//                }
//            } else {
//                // Something didn't went well. No calls for us.
//                System.out.println("API:t svarade inte, så du är nog ledig.");
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

//    /**
//     * Prints info about today. This method makes no effort trying to be of
//     * great coding standard. In fact, it looks like crap, but works well
//     * enough for this example.
//     *
//     * @param bean The bean containing all information about today.
//     */
//    public static void printDay(DayBean bean) {
//        int nbrOfNames = 0;
//
//        System.out.println("Idag är det " + bean.getDate());
//        System.out.println("Det är en " + bean.getDayOfWeek());
//        if (bean.dayIsOff()) {
//            System.out.println("Du är ledig idag.");
//        } else {
//            System.out.println("Du är inte ledig idag.");
//        }
//        if (bean.isHoliday()) {
//            System.out.println("Det är en röd dag.");
//        } else {
//            System.out.println("Det är en helt vanlig dag.");
//        }
//        System.out.print("Idag har ");
//        for (String name : bean.todaysNames) {
//            if (nbrOfNames > 0) {
//                System.out.print(" och ");
//            }
//            System.out.print(name);
//            ++nbrOfNames;
//        }
//        System.out.println(" namnsdag.");
//    }
}