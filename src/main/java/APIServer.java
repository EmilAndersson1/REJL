import controll.Controller;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

import static spark.Spark.*;

/**
 * @author "REJL"
 */
public class APIServer {

    public static void main(String[] args) {
        Controller controller = new Controller();
        port(8888);
        staticFiles.location("/static");

        /*
         * 1.1. Generate a starting page.
         */
        get("/", (req, res) -> new PebbleTemplateEngine().render(
                new ModelAndView(null, "templates/index.html")));

        /*
         * 1.2. Logged in page.
         */
        get("/loggedin", (req, res) -> new PebbleTemplateEngine().render(
                new ModelAndView(null, "templates/main.html")));

        /*
         * 2. Get weather based on location coordinates from frontend.
         * Don't have to be logged in.
         */
        get("/api/weather/:latitude/:longitude", (req, res) -> controller.getJsonWeather(
                req.params(":latitude"), req.params(":longitude"))); // json weather.

        /*
         * 3.1. Authorize user.
         * Redirects to the Spotify user authorization url and automatically to callback-endpoint (3.2.).
         * This apps access to the users spotify acount:
         * https://www.spotify.com/us/account/apps/
         */
        get("/login", (req, res) -> {
            res.redirect(controller.getStringAuthorizationUrl());
            return res.status();
        });

        /*
         * 3.2. Authorize app.
         * Redirects to logged in main page (1.2.).
         */
        get("/callback/", (req, res) -> {
            controller.getJsonToken(req.queryMap().get("code").value());
            res.redirect("/loggedin");
            return controller.getJsonUserProfile();
        });

        /*
         * 4. Get tracks based on weather (from location) and genre.
         * User has to be logged in.
         * Returns tracks as json.
         */
        get("/api/tracks/:weather/:genre", (req, res) -> controller.getJsonTracks(
                req.params(":weather"), req.params(":genre")));

        /*
         * 5. Create Playlist and add tracks to users Spotify account.
         * User has to be logged in.
         * Returns a playlist as json.
         */
        get("/api/playlist", (req, res) -> controller.getJsonPlaylist());
    }
}