package controllers;

import com.google.gson.Gson;
import model.app.ClientCredentials;
import model.spotify.Code;
import model.spotify.Playlist;
import model.spotify.Token;
import model.spotify.Tracks;
import services.*;
import services.spotify.*;
import services.yr.WeatherRetrieval;
import utils.ClientEncoder;
import utils.MoodInterpreter;

/**
 * Controller class for API:s.
 */
public class DataHandler {

    private APIService  UserLogin;
    private APIService  AppAuthentication;

    private String      encodedClientCredentials;
    private Token       authorizationToken;
    private Code        authorizationCode;

    private APIService  weatherRetrieval;
    private APIService  trackRecommendations;
    private APIService  playlistCreation;
    private APIService  tracksToPlaylistAddition;

    private String      latitude;
    private String      longitude;
    private float       valance;
    private Playlist    playlist;
    private Tracks      tracks;

    public DataHandler() {
        ClientCredentials credentials   = new ClientCredentials();

        encodedClientCredentials    = ClientEncoder.generate(credentials.getClientID(), credentials.getClientSecret());

        UserLogin                   = new UserLogin(this);
        AppAuthentication           = new AppAuthentication(this);

        weatherRetrieval            = new WeatherRetrieval(this);
        trackRecommendations        = new TrackRecommendations(this);
        playlistCreation            = new PlaylistCreation(this);
        tracksToPlaylistAddition    = new TracksToPlaylistAddition(this);
    }

    public String addSpotifyTracksToPlaylist() {
        return new Gson().toJson(tracksToPlaylistAddition.apiResponse());
    }

    public String getSpotifyPlaylist() {
        playlist = (model.spotify.Playlist) playlistCreation.apiResponse();
        return new Gson().toJson(playlist);
    }

    private String code;
    public String getCode() {
        return code;
    }
//        public String getCode() {
//        authorizationCode = (Code) spotifyAuthUserService.apiResponse();
//        System.out.println("CHECKPOINT authorizationCode: " + authorizationCode.toString());
//        return new Gson().toJson(authorizationCode);
//    }

    public String getToken(String code) {
        this.code = code;
        authorizationToken  = (Token) AppAuthentication.apiResponse();
        return new Gson().toJson(authorizationToken);
    }

    public String getWeather(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        return new Gson().toJson(weatherRetrieval.apiResponse());
    }

    public String getSpotifyTracks(String weather) {
        valance = MoodInterpreter.weatherToValance(weather);
        tracks = (Tracks) trackRecommendations.apiResponse();
        return new Gson().toJson(tracks);
    }

    public String getEncodedClientCredentials() {
        return encodedClientCredentials;
    }

    public Token getAuthorizationToken() {
        return authorizationToken;
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
