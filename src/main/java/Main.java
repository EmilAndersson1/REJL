import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class Main {

    public static void main(String[] args) {

        // Visa information i webbläsare lokalt: "http://localhost:5555"
        port(5555);

        // Plats för css-filer typ
        staticFiles.location("/static");

        // Används för marshalling/unmarshalling: java-objekt <--> JSON
        Gson gson = new Gson();

        /*
         * (CORS är ett problem när vi använder lokala HTML-filer så vi låter Spark agera webbserver och serva oss en html-fil.)
         *
         * Visa index.html via "http://localhost:5555/front"
         */
        get("/front", (req, res) -> {
            return new PebbleTemplateEngine().render(
                    new ModelAndView(null, "templates/index.html"));
        });

        //
        get("/", (req, res) -> "Start REJL");

        // test
        get("/hello", (req, res) -> "Hello World");
    }
}