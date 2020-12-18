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

        /**
         *
         *  Put latitude and longitude in url in web browser, test coordinates below.
         *  http://localhost:5555/front/latitude/longitude
         */
        get("/:latitude/:longitude", (req, res) -> {

            String latitude = req.params(":latitude");
            String longitude = req.params(":longitude");

            CurrentWeather weatherAtRequestLocation = (CurrentWeather) weatherService.getWeather(latitude, longitude);
            // TEST VALUES WITH high probability of clear skies (opposite of Malmö...):
            //Yuma, Arizona:    32.671519/-114.612754
            //Sowdari, Sudan:   15.611943/28.599067
            String moodAtRequestLocation = dataHandler.getMoodFromWeather(weatherAtRequestLocation);

            if (latitude.equals("32.671519")) {
                moodAtRequestLocation = "Current mood in Yuma, Arizona:" + moodAtRequestLocation;
            } else if (latitude.equals("15.611943")) {
                moodAtRequestLocation = "Current mood in Sowdari, Sudan:" + moodAtRequestLocation;
            }
            moodAtRequestLocation += ". Current mood in Malmö:" + mood;

            return moodAtRequestLocation;
        });

        /*
         * ---------------------------------------------------------------------------------------------------
         * NÅGRA GAMLA TESTMETODER I SPARK-STIL.
         * (CORS är ett problem när vi använder lokala HTML-filer så vi låter Spark
         * agera webbserver och serva oss en html-fil.) <- tveksamt om vi behöver bry oss om detta.
         */
        get("/", (req, res) -> {
            return new PebbleTemplateEngine().render(
                    new ModelAndView(null, "templates/index.html"));
        });
    }
}