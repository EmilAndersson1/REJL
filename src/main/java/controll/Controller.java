package controll;

import com.google.gson.Gson;
import model.app.ClientCredentials;
import model.spotify.Playlist;
import model.spotify.Token;
import model.spotify.RecommendedTracks;
import model.spotify.UserProfile;
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
    private APIService userProfileRetrieval;

    private String latitude;
    private String longitude;
    private float valance;
    private String weather;
    private String genre;
    private Playlist playlist;
    private RecommendedTracks recommendedTracks;
    private String userId;
    private boolean userLoggedIn;
    private boolean userAuthpath;
    private boolean generatedTracks;
    private UserProfile userProfile;

    public Controller() {
        userLoggedIn = false;
        userAuthpath = false;
        generatedTracks = false;

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

    public String getStringAuthorizationUrl() {
        userAuthpath = true;
        return "https://accounts.spotify.com/sv/" +
                "authorize?client_id=444dff56b04044f3b091504c069e9954&response_type=code&" +
                "redirect_uri=http:%2F%2Flocalhost:8888%2Fcallback%2F&scope=playlist-modify-public";
    }

    public String getJsonWeather(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        return new Gson().toJson(weatherRetrieval.apiResponse());
    }

    public void getJsonToken(String authorizationCode) {
        this.authorizationCode = authorizationCode;
        userLoggedIn = true;
        authorizationToken = (Token) AppAuthentication.apiResponse();
    }

    public void getJsonUserProfile() {
        userProfile = (UserProfile) userProfileRetrieval.apiResponse();
        userId = userProfile.userId;
    }

    public String getJsonTracks(String weather, String genre) {
        this.weather = weather;
        this.genre = genre;
        generatedTracks = true;
        valance = MoodInterpreter.weatherToValance(weather);
        recommendedTracks = (RecommendedTracks) trackRecommendations.apiResponse();
        return new Gson().toJson(recommendedTracks);
    }

    public String getJsonPlaylist() {
        playlist = (Playlist) playlistCreation.apiResponse(); //Create a playlist.
        playlist = (Playlist) tracksToPlaylistAddition.apiResponse(); //Add tracks to the playlist. (And update playlist)
        return new Gson().toJson(playlist);
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
        return userProfile.userId;
    }
    public String getUserName() {
        return userProfile.userName;
    }
    public String getUserPic() {
        return userProfile.images[0].url;
    }
    public String getUserUrl() {
        return userProfile.externalUrls.spotifyUrl;
    }

    public boolean userIsLoggedIn() {
        return userLoggedIn;
    }

    public boolean userAuthpathIsGenerated() {
        return userAuthpath;
    }

    public boolean hasGeneratedTracks() {
        return generatedTracks;
    }
}
