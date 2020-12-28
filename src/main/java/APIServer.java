import Controllers.DataHandler;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

import static spark.Spark.*;

/**
 * Mål: 1. Visa information på en html-sida.
 *      2. Använda som API.
 *
 *      CORS är ett problem med lokala HTML-filer. Låt Spark agera webbserver och serva en html-fil.
 *
 * @author "REJL"
 */
public class APIServer {

    public static void main(String[] args) {

        DataHandler dataHandler = new DataHandler(); // TODO: Think about where to put DataHandler. User?

        port(5555); //Webbläsare: "http://localhost:5555"
        staticFiles.location("/static"); // Plats för css-filer typ

        // Get weather based on location coordinates from frontend.
        // Return json weather.
        get("/:latitude/:longitude", (req, res) -> {
            String latitude = req.params(":latitude");
            String longitude = req.params(":longitude");
            return dataHandler.getWeather(latitude, longitude);
        });

        // Get tracks based on weather.
        get("/:weather", (req, res) -> {
            String weather = req.params(":weather");
            return dataHandler.getTracks(weather);
        });

        // Generate a starting page.
        get("/", (req, res) -> {
            return new PebbleTemplateEngine().render(
                    new ModelAndView(null, "templates/index.html"));
        });
    }
}