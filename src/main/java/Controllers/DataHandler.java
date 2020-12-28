package Controllers;

import com.google.gson.Gson;
import model.CurrentWeather;
import model.Playlist;
import model.Token;
import model.Track;
import services.*;

import java.util.Base64;

/**
 * Controller class for API:s.
 */
public class DataHandler {

    private APIService weatherService;
    private APIService spotifyTrackService;
    private APIService spotifyValanceSearchService;

    private String encodedClientCredentials;
    private Token authorizationToken;

    // TODO: Test values. Do something about this.
    private String requestTrack = "6UbwfQeAEzhnolDwXiPowY"; // TODO: Dynamic track. Track object...
    private String latitude;
    private String longitude;

    private float valance;

    public DataHandler() {
        APIService spotifyAuthAppService = new SpotifyAuthAppService(this);
        // TODO: Prompt us - the admins - to type in ID and Secret. Or other meassure for security.
        String clientID = "444dff56b04044f3b091504c069e9954";
        String clientSecret = "a15ce62d40ba4a0697be814f59602ebc";
        encodedClientCredentials = generateEncodedClientCredentials(clientID, clientSecret);
        //TODO: Generate a new token if expired. This is only instantiated once after starting server right now.
        authorizationToken = (Token) spotifyAuthAppService.apiResponse();

        weatherService = new WeatherService(this);
        spotifyTrackService = new SpotifyTrackService(this);
        spotifyValanceSearchService = new SpotifyValanceSearchService(this);
    }

    // TODO: Hide this better?
    private String generateEncodedClientCredentials(String clientID, String clientSecret) {
        return Base64.getEncoder().encodeToString((clientID + ":" + clientSecret).getBytes());
    }

    // Test if API:s return correctly.
    public String testGetWeatherAndSong(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

        CurrentWeather currentWeather = (CurrentWeather) weatherService.apiResponse();
        Track track = (Track) spotifyTrackService.apiResponse();

        System.out.println("Weather: " + currentWeather.symbol_code + ". Track: " + track.name); //Test print
        return "Weather: " + currentWeather.symbol_code + ". Track: " + track.name;
    }

    public String getWeather(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

        CurrentWeather currentWeather = (CurrentWeather) weatherService.apiResponse();

        return new Gson().toJson(currentWeather);
    }

    public String getTracks(String weather) {

        // weather -> valance
        valance = mapWeatherToValance(weather);

        System.out.println("TEST: Mapped Valance from weather: " + valance);

        // valance -> track
        Playlist playlist = (Playlist) spotifyValanceSearchService.apiResponse();

        return new Gson().toJson(playlist);
    }

    public String getEncodedClientCredentials() {
        return encodedClientCredentials;
    }

    public Token getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(Token authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getRequestTrack() {
        return requestTrack;
    }

    public void setRequestTrack(String requestTrack) {
        this.requestTrack = requestTrack;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public float getValance() {
        return valance;
    }

    public void setValance(float valance) {
        this.valance = valance;
    }

    /*
    * Ratings are subjectively mapped based on the developers collective
    * and completely biased experiences.
    * To make more accurate music predictions is out of the scope of the assignment.
    * This could be improved in the future.
     */
    private float mapWeatherToValance(String weather) {
        float valance;

        switch (weather) {
            case "clearsky":
                valance = 0.900f;
                break;
            case "cloudy":
            case "fog":
                valance = 0.550f;
                break;
            case "fair":
                valance = 0.750f;
                break;
            case "heavyrain":
            case "heavyrainandthunder":
            case "heavyrainshowers":
            case "heavyrainshowersandthunder":
            case "snowandthunderr":
            case "rain":
            case "rainandthunder":
            case "rainshowers":
            case "rainshowersandthunder":
                valance = 0.300f;
                break;
            case "heavysleet":
            case "heavysleetandthunder":
            case "heavysleetshowersandthundert":
                valance = 0.200f;
                break;
            case "heavysnow":
            case "heavysnowandthunder":
            case "heavysnowshowers":
            case "heavysnowshowersandthunder":
            case "snowshowersandthunder":
            case "lightsnow":
            case "lightsnowandthunder":
            case "lightsnowshowers":
            case "lightssleetshowersandthunder":
            case "lightssnowshowersandthunder":
                valance = 0.400f;
                break;
            case "lightrain":
            case "lightrainandthunder":
            case "lightrainshowers":
            case "lightrainshowersandthunder":
                valance = 0.450f;
                break;
            case "lightsleet":
            case "lightsleetandthunder":
            case "lightsleetshowers":
            case "sleet":
            case "sleetandthunder":
            case "sleetshowers":
            case "sleetshowersandthunder":
                valance = 0.350f;
                break;
            case "partlycloudy":
            case "snow":
                valance = 0.800f;
                break;
            case "snowshowers":
                valance = 0.700f;
                break;

            default:
                valance = 0.500f; // Average valance.
//                //TODO: Exception ?
//                throw new IllegalStateException("Unexpected value: " + weather);
        }
        return valance;
    }
}
