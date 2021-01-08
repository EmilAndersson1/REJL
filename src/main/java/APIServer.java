import controll.Controller;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * @author "REJL"
 */
public class APIServer {

    public static void main(String[] args) {
        Controller controller = new Controller();
        port(8888);
        staticFiles.location("/static");

        before("/", (req, res) -> {
            if (!controller.userIsLoggedIn()) {
                res.redirect("/login");
            }
        });

        before("/callback/*", (req, res) -> {
            if (!controller.userAuthpathIsGenerated()) {
                halt(401, "Spotify not authenticated!");
            }
        });

        before("/api/*", (req, res) -> {
            if (!controller.userIsLoggedIn()) {
                halt(401, "Not Logged in!");
            }
        });

        before("/api/playlist", (req, res) -> {
            if (!controller.hasGeneratedTracks()) {
                halt(418, "No tracks!");
            }
        });

        before("/api/playlist/", (req, res) -> {
            if (!controller.hasGeneratedTracks()) {
                halt(418, "No tracks!");
            }
        });

        /*
         * 1.1. Generates a starting page. Available after log in.
         */
        get("/", (req, res) -> {
            controller.getJsonUserProfile();
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("userId", controller.getUserId());
            model.put("userName", controller.getUserName());
            model.put("userPic", controller.getUserPic()); //bild
            model.put("UserUrl", controller.getUserUrl());

            return new PebbleTemplateEngine().render(
                    new ModelAndView(model, "templates/index.html"));
        });

        /*
         * 1.2. Generates a log in page.
         */
        get("/login", (req, res) -> new PebbleTemplateEngine().render(
                new ModelAndView(null, "templates/login.html")));

        /*
         * 2.1. Authorize user.
         * Redirects to the Spotify user authorization url and automatically to callback-endpoint.
         * This apps access to the user Spotify account kan be removed here:
         * https://www.spotify.com/us/account/apps/
         */
        get("/loginButton", (req, res) -> {
            res.redirect(controller.getStringAuthorizationUrl());
            return res.status();
        });

        /*
         * 2.2. Authorize app.
         * Automatically redirects to logged in main starting page.
         */
        get("/callback/", (req, res) -> {
            controller.getJsonToken(req.queryMap().get("code").value());
            res.redirect("/");
            return res.status();
        });

        /*
         * 3. Get weather based on location coordinates.
         * Don't have to be logged in?
         */
        get("/api/weather/:latitude/:longitude", (req, res) -> controller.getJsonWeather(
                req.params(":latitude"), req.params(":longitude"))); // json weather.

        /*
         * 4. Get tracks based on weather (from location) and genre.
         * User has to be logged in.
         * Returns tracks as json.
         */
        get("/api/tracks/:weather/:genre", (req, res) -> controller.getJsonTracks(
                req.params(":weather"), req.params(":genre")));

        /*
         * 5. Create Playlist and add the previously generated tracks to users Spotify account.
         * User has to be logged in.
         * Returns a playlist as json.
         */
        post("/api/playlist", (req, res) -> controller.getJsonPlaylist());
    }
}