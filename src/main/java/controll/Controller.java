package controll;

import com.google.gson.Gson;
import model.app.ClientCredentials;
import model.spotify.Playlist;
import model.spotify.Token;
import model.spotify.RecommendedTracks;
import model.spotify.UserProfile;
import model.yr.Weather;
import services.*;
import services.spotify.*;
import services.yr.WeatherRetrieval;
import utils.ClientEncoder;
import utils.MoodInterpreter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private APIService userProfileRetrieval;

    private String latitude;
    private String longitude;
    private float valance;
    private String weather;
    private String genre;
    private Playlist playlist;
    private RecommendedTracks recommendedTracks;
    private String userId;

    public Controller() {
        ClientCredentials credentials = new ClientCredentials();

        encodedClientCredentials = ClientEncoder.generate(credentials.getClientID(), credentials.getClientSecret());

        UserLogin                   = new UserLogin(this);
        AppAuthentication           = new AppAuthentication(this);
        userProfileRetrieval        = new UserProfileRetrieval(this);

        weatherRetrieval            = new WeatherRetrieval(this);
        trackRecommendations        = new TrackRecommendations(this);
        playlistCreation            = new PlaylistCreation(this);
        tracksToPlaylistAddition    = new TracksToPlaylistAddition(this);
    }

    public String getSpotifyPlaylist() {
        playlist = (Playlist) playlistCreation.apiResponse(); //Create a playlist.
        playlist = (Playlist) tracksToPlaylistAddition.apiResponse(); //Add tracks to the playlist. (And update playlist)
        return new Gson().toJson(playlist);
    }

    public String getSpotifyAuthorizationLink() {
        return "https://accounts.spotify.com/sv/" +
                "authorize?client_id=444dff56b04044f3b091504c069e9954&response_type=code&" +
                "redirect_uri=http:%2F%2Flocalhost:8888%2Fcallback%2F&scope=playlist-modify-public";
    }

    public String getUserProfile() {
        UserProfile userProfile = (UserProfile) userProfileRetrieval.apiResponse();
        userId = userProfile.userId;
        return new Gson().toJson(userProfile);
    }

    public void getSpotifyToken(String authorizationCode) {
        this.authorizationCode = authorizationCode;
        authorizationToken = (Token) AppAuthentication.apiResponse();
        new Gson().toJson(authorizationToken);
    }

    public String getWeather(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

        Weather weather = (Weather) weatherRetrieval.apiResponse();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String str = LocalDateTime.now().format(formatter);
        System.out.println(str.substring(11,13));
        for (int i = 0; i < 5; i++) {
            System.out.println(weather.weathers[i].time.substring(11,13));
            if (str.substring(11,13).equals(weather.weathers[i].time.substring(11,13))) {
                System.out.println(weather.weathers[i]);
                return new Gson().toJson(weather.weathers[i]);
            }
        }
        return "something wrong";
    }

    public String getSpotifyTracks(String weather, String genre) {
        this.weather = weather;
        this.genre = genre;
        valance = MoodInterpreter.weatherToValance(weather);
        recommendedTracks = (RecommendedTracks) trackRecommendations.apiResponse();
        return new Gson().toJson(recommendedTracks);
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

    public String getWeather() {
        return weather;
    }

    public String getGenre() {
        return genre;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public RecommendedTracks getRecommendedTracks() {
        return recommendedTracks;
    }

    public String getUserId() {
        return userId;
    }
}
