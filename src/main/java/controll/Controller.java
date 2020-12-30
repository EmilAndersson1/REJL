package controll;

import com.google.gson.Gson;
import model.app.ClientCredentials;
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
public class Controller {

    private APIService UserLogin;
    private APIService AppAuthentication;

    private String encodedClientCredentials;
    private Token authorizationToken;
    private String authorizationCode;

    private APIService weatherRetrieval;
    private APIService trackRecommendations;
    private APIService playlistCreation;
    private APIService tracksToPlaylistAddition;

    private String latitude;
    private String longitude;
    private float valance;
    private String genre;
    private Playlist playlist;
    private Tracks tracks;

    public Controller() {
        ClientCredentials credentials = new ClientCredentials();

        encodedClientCredentials = ClientEncoder.generate(credentials.getClientID(), credentials.getClientSecret());

        UserLogin = new UserLogin(this);
        AppAuthentication = new AppAuthentication(this);

        weatherRetrieval = new WeatherRetrieval(this);
        trackRecommendations = new TrackRecommendations(this);
        playlistCreation = new PlaylistCreation(this);
        tracksToPlaylistAddition = new TracksToPlaylistAddition(this);
    }

    public String getSpotifyPlaylist() {
        playlist = (Playlist) playlistCreation.apiResponse();
        tracksToPlaylistAddition.apiResponse();
        return new Gson().toJson(playlist);
    }

    public String getSpotifyAuthorizationLink() {
        return "https://accounts.spotify.com/sv/" +
                "authorize?client_id=444dff56b04044f3b091504c069e9954&response_type=code&" +
                "redirect_uri=http:%2F%2Flocalhost:8888%2Fcallback%2F&scope=playlist-modify-public";
    }

    public String getSpotifyToken(String authorizationCode) {
        this.authorizationCode = authorizationCode;
        authorizationToken = (Token) AppAuthentication.apiResponse();
        return new Gson().toJson(authorizationToken);
    }

    public String getWeather(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        return new Gson().toJson(weatherRetrieval.apiResponse());
    }

    public String getSpotifyTracks(String weather, String genre) {
        this.genre = genre;
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

    public String getAuthorizationCode() {
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

    public String getGenre() {
        return genre;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public Tracks getTracks() {
        return tracks;
    }
}
