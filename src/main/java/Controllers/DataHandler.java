package Controllers;

import model.CurrentWeather;
import model.Token;
import model.Track;
import services.APIService;
import services.SpotifyAuthAppService;
import services.SpotifyTrackService;
import services.WeatherService;

import java.util.Base64;

/**
 * Controller class for API:s.
 */
public class DataHandler {

    private APIService weatherService;
    private APIService spotifyTrackService;

    private String encodedClientCredentials;
    private Token authorizationToken;

    // TODO: Test values. Do something about this.
    private String requestTrack = "6UbwfQeAEzhnolDwXiPowY"; // TODO: Dynamic track. Track object...
    private String latitude;
    private String longitude;


    public DataHandler() {
        APIService spotifyAuthAppService = new SpotifyAuthAppService(this);
        // TODO: Prompt us - the admins - to type in ID and Secret. Or other meassure for security.
        String clientID             = "444dff56b04044f3b091504c069e9954";
        String clientSecret         = "a15ce62d40ba4a0697be814f59602ebc";
        encodedClientCredentials    = generateEncodedClientCredentials(clientID, clientSecret);
        //TODO: Generate a new token if expired. This is only instantiated once after starting server right now.
        authorizationToken          = (Token) spotifyAuthAppService.apiResponse();

        weatherService              = new WeatherService(this);
        spotifyTrackService         = new SpotifyTrackService(this);
    }

    // TODO: Hide this better?
    private String generateEncodedClientCredentials(String clientID, String clientSecret) {
        return Base64.getEncoder().encodeToString((clientID + ":" + clientSecret).getBytes());
    }

    // Test if API:s return correctly.
    public String testGetWeatherAndSong(String latitude, String longitude) {
        this.latitude   = latitude;
        this.longitude  = longitude;

        CurrentWeather currentWeather   = (CurrentWeather) weatherService.apiResponse();
        Track track                     = (Track) spotifyTrackService.apiResponse();

        System.out.println("Weather: " + currentWeather.symbol_code + ". Track: " + track.name); //Test print
        return "Weather: " + currentWeather.symbol_code + ". Track: " + track.name;
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
}
