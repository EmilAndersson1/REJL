import controll.Controller;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

import static spark.Spark.*;

/**
 *
 * @author "REJL"
 */
public class APIServer {

    public static void main(String[] args) {
        Controller controller = new Controller();
        port(8888);
        staticFiles.location("/static");

        /*
         * 1. Generate a starting page.
         * CORS är ett problem med lokala HTML-filer. Låt Spark agera webbserver och serva en html-fil.
         */
        get("/", (req, res) -> new PebbleTemplateEngine().render(
                new ModelAndView(null, "templates/index.html")));

        /*
         * 2. Get weather based on location coordinates from frontend.
         */
        get("/api/weather/:latitude/:longitude", (req, res) -> controller.getWeather(
                req.params(":latitude"), req.params(":longitude"))); // json weather.

        /*
         * 3.1. Authorize user.
         * Remove this apps access to users spotify acount to log in again: https://www.spotify.com/us/account/apps/
         */
        get("/login", (req, res) -> controller.getSpotifyAuthorizationLink());

        /*
         * 3.2. Authorize app.
         */
        get("/callback/", (req, res) -> controller.getSpotifyToken(
                req.queryMap().get("code").value()));

        /*
         * 5. Get tracks based on weather. Returns tracks as json.
         */
        get("/api/tracks/:weather/:genre", (req, res) -> controller.getSpotifyTracks(
                req.params(":weather"), req.params(":genre")));

        /*
         * 6. Create Playlist and add tracks. Returns a playlist as json.
         */
        get("/api/playlist", (req, res) -> controller.getSpotifyPlaylist());
    }
}