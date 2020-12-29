import Controllers.DataHandler;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

import static spark.Spark.*;

/**
 * CORS är ett problem med lokala HTML-filer. Låt Spark agera webbserver och serva en html-fil.
 *
 * @author "REJL"
 */
public class APIServer {

    public static void main(String[] args) {

        DataHandler dataHandler = new DataHandler(); // TODO: Think about where to put DataHandler. User?

        port(8888); //Webbläsare: "http://localhost:8888"
        staticFiles.location("/static"); // Plats för css-filer typ

        // Get weather based on location coordinates from frontend.
        // Return json weather.
        get("/weather/:latitude/:longitude", (req, res) -> {
            String latitude = req.params(":latitude");
            String longitude = req.params(":longitude");
            return dataHandler.getWeather(latitude, longitude);
        });

        // Get tracks based on weather.
        get("/tracks/:weather", (req, res) -> {
            String weather = req.params(":weather");
            return dataHandler.getSpotifyTracks(weather);
        });

        // Authorize user.
        get("/login", (req, res) -> {

            //TODO: returnera urlen som en sträng eller ?
            HttpResponse<String> stringResponse = Unirest.get("https://accounts.spotify.com/authorize")
                    .queryString("client_id", "444dff56b04044f3b091504c069e9954") //dataHandler.getClientId()
                    .queryString("response_type", "code")
                    .queryString("redirect_uri", "http://localhost:8888/callback/")
                    .queryString("scope", "playlist-modify-public")
                    .asString();

            return "https://accounts.spotify.com/sv/authorize?client_id=444dff56b04044f3b091504c069e9954&response_type=code&redirect_uri=http:%2F%2Flocalhost:8888%2Fcallback%2F&scope=playlist-modify-public";
        });

        // Authorize app.
        get("/callback/", (req, res) -> {
            String code = req.queryMap().get("code").value();
            return dataHandler.getToken(code);
        });

        // Create Playlist.
        get("/playlist", (req, res) -> {
            return dataHandler.getSpotifyPlaylist();
        });

        // Add tracks to Playlist.
        get("/addtracks", (req, res) -> {
            return dataHandler.addSpotifyTracksToPlaylist();
        });

        // Generate a starting page.
        get("/", (req, res) -> {
            return new PebbleTemplateEngine().render(
                    new ModelAndView(null, "templates/index.html"));
        });
    }
}