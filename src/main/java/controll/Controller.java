package controll;

import com.google.gson.Gson;
import model.app.ClientCredentials;
import model.spotify.Playlist;
import model.spotify.Token;
import model.spotify.UserProfile;
import services.*;
import services.spotify.*;
import services.yr.WeatherRetrieval;
import utils.ClientEncoder;
import utils.MoodInterpreter;

/**
 * Controller class for coordination between the servers API endpoints on one side and the
 * service calls to external API:s.
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
 */
public class Controller {

    private APIService AppAuthentication;
    private ClientCredentials clientCredentials;

    private String encodedClientCredentials;
    private Token authorizationToken;
    private String authorizationCode;

    private APIService weatherRetrieval;
    private APIService trackRecommendations;
    private APIService playlistCreation;
//    private APIService tracksToPlaylistAddition;
    private APIService userProfileRetrieval;

    private String latitude;
    private String longitude;
    private float valance;
    private String genre;
    private String tracks;
    private Playlist playlist;
    private boolean userLoggedIn;
    private boolean userAuthpath;
    private boolean generatedTracks;
    private UserProfile userProfile;

    public Controller() {
        userLoggedIn = false;
        userAuthpath = false;
        generatedTracks = false;

        clientCredentials = new ClientCredentials();
        encodedClientCredentials = ClientEncoder.generate(
                clientCredentials.getClientID(), clientCredentials.getClientSecret());

        AppAuthentication           = new AppAuthentication(this);
        userProfileRetrieval        = new UserProfileRetrieval(this);

        weatherRetrieval            = new WeatherRetrieval(this);
        trackRecommendations        = new TrackRecommendations(this);
        playlistCreation            = new PlaylistCreation(this);
//        tracksToPlaylistAddition    = new TracksToPlaylistAddition(this);
    }

    public String getStringAuthorizationUrl() {
        userAuthpath = true;
        return clientCredentials.getAuthorizationUrl();
    }

    public String getJsonToken(String authorizationCode) {
        this.authorizationCode = authorizationCode;
        userLoggedIn = true;
        authorizationToken = (Token) AppAuthentication.apiResponse();
        return new Gson().toJson(authorizationToken);
    }

    public void getJsonUserProfile() {
        userProfile = (UserProfile) userProfileRetrieval.apiResponse();
    }

    public String getJsonWeather(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        return new Gson().toJson(weatherRetrieval.apiResponse());
    }

    public String getJsonTracks(String weather, String genre) {
        this.genre = genre;
        generatedTracks = true;
        valance = MoodInterpreter.weatherToValance(weather);
        return new Gson().toJson(trackRecommendations.apiResponse());
    }

    public String getJsonPlaylist(String tracks) {
        this.tracks = tracks;
        playlist = (Playlist) playlistCreation.apiResponse();
//        playlist = (Playlist) tracksToPlaylistAddition.apiResponse();
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

    public String getGenre() {
        return genre;
    }

    public Playlist getPlaylist() {
        return playlist;
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

    public String getTracks() {
        return tracks;
    }
}
