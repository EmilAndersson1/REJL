import Controllers.DataHandler;
import model.CurrentWeather;
import services.WeatherService;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

import static spark.Spark.*;

/**
 * Mål: 1. Visa information på en html-sida med hjälp av ramverket Spark.
 *      2. Använda som API.
 *
 * @author "REJL"
 */
public class APIServer {

    public static void main(String[] args) {

        // Object to get weather from weather API (currently YR).
        WeatherService weatherService = new WeatherService();
        // Translate and store a response information to a java object. //TODO: get coordinates from frontend (javaSpark<->ajax).
        CurrentWeather weather = (CurrentWeather) weatherService.getWeather("55.611039", "12.995168"); //MAU
        // Simple testing class to simulate a mood. //TODO: After working connection to Spotify: make something real.
        DataHandler dataHandler = new DataHandler();
        // Simple testing to extract a mood based on: is it is raining or not?
        String mood = dataHandler.getMoodFromWeather(weather);
        // This should actually somewhat accurately describe the current weather at MAU.
        // Could possibly be translated to Spotify valance like this or whatever.
        System.out.println("\nCURRENT WEATHER MOOD:\n" + mood);


        // Visa information i webbläsare lokalt: "http://localhost:5555"
        port(5555);
        // Plats för css-filer typ
        staticFiles.location("/static");

        //TODO: This is just an idea for a method. Does it work like this maybe? Very sketchy, not tested...
        // A method like this should be called from a frontend through some javascript/jQuery/ajax-method.
        get("/front?lat=:latitude&lon=:longitude", (req, res) -> {

            // Can we get coordinates from frontend by it adding query parameters in the uri?
            // And then pick it up like this somehow?
            String lat = req.queryMap().get("latitude").value();
            String lon = req.queryMap().get("longitude").value();

            // Call a method that generates a Spotify json-response based on lon and lat variables vi weatherService.
            // SomeClass.generateMusic();

            // Is this the way to send back a response to frontend?
            res.body("Some generated Spotify json-object");

            return new PebbleTemplateEngine().render(
                    new ModelAndView(null, "templates/index.html"));
        });

        /*
         * ---------------------------------------------------------------------------------------------------
         * NÅGRA GAMLA TESTMETODER I SPARK-STIL.
         * (CORS är ett problem när vi använder lokala HTML-filer så vi låter Spark
         * agera webbserver och serva oss en html-fil.) <- tveksamt om vi behöver bry oss om detta.
         */
        get("/front", (req, res) -> {
            return new PebbleTemplateEngine().render(
                    new ModelAndView(null, "templates/index.html"));
        });
        get("/", (req, res) -> "Start REJL");
        get("/hello", (req, res) -> "Hello World");
    }
}