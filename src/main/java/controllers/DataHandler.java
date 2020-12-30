package controllers;

import com.google.gson.Gson;
import model.app.ClientCredentials;
import model.spotify.Code;
import model.spotify.Playlist;
import model.spotify.Token;
import model.spotify.Tracks;
import services.*;
import services.spotify.*;
import services.yr.WeatherService;
import utils.ClientEncoder;
import utils.MoodInterpreter;

/**
 * Controller class for API:s.
 */
public class DataHandler {

    private APIService spotifyAuthUserService;
    private APIService spotifyAuthUserAppService;

    private APIService weatherService;
    private APIService spotifyValanceSearchService;
    private APIService addTracksToPlaylist;

    private APIService spotifyPlaylistService;

    private String  clientId;
    private String  encodedClientCredentials;
    private Token authorizationToken;
    private Code authorizationCode;
    private String  latitude;
    private String  longitude;
    private float   valance;

    private Playlist playlist;
    private Tracks tracks;

    public DataHandler() {
//        APIService spotifyAuthAppService = new SpotifyAuthAppService(this);
        spotifyAuthUserService       = new SpotifyAuthUserService(this);
        spotifyAuthUserAppService    = new SpotifyAuthUserAppService(this);

        ClientCredentials credentials = new ClientCredentials();
        clientId                    = credentials.getClientID();
        encodedClientCredentials    = ClientEncoder.generate(credentials.getClientID(), credentials.getClientSecret());

//        authorizationToken = (Token) spotifyAuthAppService.apiResponse();

        weatherService              = new WeatherService(this);
        spotifyValanceSearchService = new SpotifyValanceSearchService(this);
        spotifyPlaylistService      = new SpotifyPlaylistService(this);
        addTracksToPlaylist         = new AddTracksToPlaylist(this);
    }

    public String addSpotifyTracksToPlaylist() {
        return new Gson().toJson(addTracksToPlaylist.apiResponse());
    }

    public String getSpotifyPlaylist() {
        playlist = (Playlist) spotifyPlaylistService.apiResponse();
        return new Gson().toJson(playlist);
    }

    private String code;
    public String getCode() {
        return code;
    }
    public String getToken(String code) {
        this.code = code;
        authorizationToken  = (Token) spotifyAuthUserAppService.apiResponse();
        return new Gson().toJson(authorizationToken);
    }

//    public String getCode() {
//        authorizationCode = (Code) spotifyAuthUserService.apiResponse();
//        System.out.println("CHECKPOINT authorizationCode: " + authorizationCode.toString());
//        return new Gson().toJson(authorizationCode);
//    }

    public String getWeather(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        return new Gson().toJson(weatherService.apiResponse());
    }

    public String getSpotifyTracks(String weather) {
        valance = MoodInterpreter.weatherToValance(weather);
        tracks = (Tracks) spotifyValanceSearchService.apiResponse();
        return new Gson().toJson(tracks);
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

    public Playlist getPlaylist() {
        return playlist;
    }

    public Tracks getTracks() {
        return tracks;
    }
}
