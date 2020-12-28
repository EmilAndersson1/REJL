package Controllers;

import com.google.gson.Gson;
import model.ClientCredentials;
import model.Code;
import model.Token;
import services.*;
import utils.ClientEncoder;
import utils.MoodInterpreter;

/**
 * Controller class for API:s.
 */
public class DataHandler {

    private APIService weatherService;
    private APIService spotifyValanceSearchService;

    private String  clientId;
    private String  encodedClientCredentials;
    private Token   authorizationToken;
    private Code    authorizationCode;
    private String  latitude;
    private String  longitude;
    private float   valance;

    public DataHandler() {
        APIService spotifyAuthAppService = new SpotifyAuthAppService(this);
        APIService spotifyAuthUserService = new SpotifyAuthUserService(this);
        ClientCredentials credentials = new ClientCredentials();

        clientId = credentials.getClientID();
        encodedClientCredentials = ClientEncoder.generate(credentials.getClientID(), credentials.getClientSecret());

        //TODO: Generate a new token if expired. This is only instantiated once after starting server right now.
        authorizationToken = (Token) spotifyAuthAppService.apiResponse();

        authorizationCode = (Code) spotifyAuthUserService.apiResponse();

        weatherService = new WeatherService(this);
        spotifyValanceSearchService = new SpotifyValanceSearchService(this);
    }

    public String getWeather(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        return new Gson().toJson(weatherService.apiResponse());
    }

    public String getTracks(String weather) {
        valance = MoodInterpreter.weatherToValance(weather);
        return new Gson().toJson(spotifyValanceSearchService.apiResponse());
    }

    public String getClientId() {
        return clientId;
    }

    public String getEncodedClientCredentials() {
        return encodedClientCredentials;
    }

    public Token getAuthorizationToken() {
        return authorizationToken;
    }

    public Code getAuthorizationCode() {
        return authorizationCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public float getValance() {
        return valance;
    }
}
