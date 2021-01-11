import controll.Controller;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * Starting point for the application server and frontend.
 *
 * Endpoints for a running API.
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
 */
public class APIServer {

    public static void main(String[] args) {
        Controller controller = new Controller();

        port(8888); // Connect to base url: http://localhost/8888
        staticFiles.location("/static");

        /*
         * User has to be authenticated to Spotify and accept authorization from app server before API-calls.
         */
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
            if (!controller.userAuthpathIsGenerated()) {
                halt(401, "Spotify not authenticated!");
            }
        });
        before("/api/playlist/", (req, res) -> {
            if (!controller.userAuthpathIsGenerated()) {
                halt(401, "Spotify not authenticated!");
            }
        });

        /*
         * 1.1. Generates a starting page.
         * Available after user authentication and authorization.
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
         * User is routed here before all other endpoints.
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
            String token = controller.getJsonToken(req.queryMap().get("code").value());
            res.redirect("/");
            return token;
        });

        /*
         * 3. Get weather based on location coordinates.
         * User has to be authenticated and authorized.
         * Returns a playlist as json.
         */
        get("/api/weather/:latitude/:longitude", (req, res) -> controller.getJsonWeather(
                req.params(":latitude"), req.params(":longitude")));

        /*
         * 4. Get tracks based on weather (from location) and genre.
         * User has to be authenticated and authorized.
         * Returns tracks as json.
         */
        get("/api/tracks/:weather/:genre", (req, res) -> controller.getJsonTracks(
                req.params(":weather"), req.params(":genre")));

        /*
         * 5. Create Playlist and add the previously generated tracks to users Spotify account.
         * User has to be authenticated and authorized.
         * Returns a playlist as json.
         */
        post("/api/playlist/", (req, res) -> controller.getJsonPlaylist(req.body()));

        /*
         * API documentation.
         */
        get("/apidoc", (req, res) -> {
            return new PebbleTemplateEngine().render(
                    new ModelAndView(null, "templates/apidoc.html"));
        });
    }
}