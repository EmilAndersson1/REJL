import Controllers.DataHandler;
import model.Location;
import services.WeatherService;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

import static spark.Spark.*;

/**
 * Visa information på en html-sida med hjälp av ramverket Spark.
 *
 * TODO: hämta information från webb-API:er
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

        /*
         * (CORS är ett problem när vi använder lokala HTML-filer så vi låter Spark agera webbserver och serva oss en html-fil.)
         *
         * Visa index.html via "http://localhost:5555/front"
         */
        get("/front", (req, res) -> {
            return new PebbleTemplateEngine().render(
                    new ModelAndView(null, "templates/testing.html"));
        });

        //
        get("/", (req, res) -> "Start REJL");

        // test
        get("/hello", (req, res) -> "Hello World");
    }
}