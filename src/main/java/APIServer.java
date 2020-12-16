import Controllers.DataHandler;
import model.Location;
import services.WeatherService;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

import static spark.Spark.*;

/**
 * Visa information på en html-sida med hjälp av ramverket Spark.
 *
 * @author "REJL"
 */
public class APIServer {

    public static void main(String[] args) {

        // Some hard coded stuff.
        WeatherService weatherService = new WeatherService();
        Location location = (Location) weatherService.getWeather("-16.516667", "-68.166667");
        DataHandler dataHandler = new DataHandler();
        String mood = dataHandler.getMoodFromWeather(location);
        System.out.println("Current weather mood: " + mood);


        // Visa information i webbläsare lokalt: "http://localhost:5555"
        port(5555);

        // Plats för css-filer typ
        staticFiles.location("/static");

        //TODO: This is just an idea for a method. Does it work like this maybe?
        // This method sholuld be called from a frontend through some javascript/jQuery/ajax-method.
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
         * TEST
         * (CORS är ett problem när vi använder lokala HTML-filer så vi låter Spark
         * agera webbserver och serva oss en html-fil.)
         */
        get("/front", (req, res) -> {
            return new PebbleTemplateEngine().render(
                    new ModelAndView(null, "templates/index.html"));
        });

        // TEST
        get("/", (req, res) -> "Start REJL");

        // TEST
        get("/hello", (req, res) -> "Hello World");
    }
}