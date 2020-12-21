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

<<<<<<< HEAD
        get("/front/:latitude/:longitude", (req, res) -> {
=======
        /**
         *
         *  Put latitude and longitude in url in web browser, test coordinates below.
         *  http://localhost:5555/latitude/longitude
         */
        get("/:latitude/:longitude", (req, res) -> {
>>>>>>> frontend

            String latitude = req.params(":latitude");
            String longitude = req.params(":longitude");
            

<<<<<<< HEAD
            return dataHandler.testGetWeatherAndSong(latitude, longitude);
        });

        get("/front", (req, res) -> {
            return new PebbleTemplateEngine().render(
                    new ModelAndView(null, "templates/index.html"));
        });

        get("/", (req, res) -> "Start REJL");
=======
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
>>>>>>> frontend
    }
}