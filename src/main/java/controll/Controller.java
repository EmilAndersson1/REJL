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

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for API:s.
 */
public class Controller {

    private APIService AppAuthentication;

    private ClientCredentials clientCredentials;
    private String encodedClientCredentials;
    private Map<String, Token> authorizedUsers;
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
    private String genre;
    private Playlist playlist;
    private boolean userAuthpath;
    private UserProfile userProfile;

    public Controller() {
        authorizedUsers = new HashMap<String, Token>();
        userAuthpath = false;
        clientCredentials = new ClientCredentials();
        encodedClientCredentials = ClientEncoder.generate(clientCredentials.getClientID(), clientCredentials.getClientSecret());
        AppAuthentication           = new AppAuthentication(this);
        userProfileRetrieval        = new UserProfileRetrieval(this);

        weatherRetrieval            = new WeatherRetrieval(this);
        trackRecommendations        = new TrackRecommendations(this);
        playlistCreation            = new PlaylistCreation(this);
        tracksToPlaylistAddition    = new TracksToPlaylistAddition(this);
    }

    public String getStringAppAuthorizationUrl() {
        userAuthpath = true;
        return "https://accounts.spotify.com/sv/authorize" +
                "?client_id=" +      clientCredentials.getClientID() +
                "&response_type=" + "code" +
                "&redirect_uri=" +  "http:%2F%2Flocalhost:8888%2Fcallback%2F" +
                "&scope=" +         "playlist-modify-public";
    }

    public String getJsonWeather(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        return new Gson().toJson(weatherRetrieval.apiResponse());
    }

    public void getJsonToken(String authorizationCode) {
        this.authorizationCode = authorizationCode;
        authorizationToken = (Token) AppAuthentication.apiResponse();
        userProfile = (UserProfile) userProfileRetrieval.apiResponse();
        authorizedUsers.put(userProfile.userId, authorizationToken);
        System.out.println("test1111: " + authorizedUsers.get(userProfile.toString()));
    }

    public String getJsonTracks(String weather, String genre) {
        this.genre = genre;
        valance = MoodInterpreter.weatherToValance(weather);
        return new Gson().toJson(trackRecommendations.apiResponse());
    }

    String tracks;
    public String getJsonPlaylist(String tracks) {
        this.tracks = tracks;
        playlist = (Playlist) playlistCreation.apiResponse();
        playlist = (Playlist) tracksToPlaylistAddition.apiResponse();
        return new Gson().toJson(playlist);
    }

    public String getClientId() {
        return clientCredentials.getClientID();
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

    public boolean userIsAuthorized(String userId){
        System.out.println("test1111: " + authorizedUsers.get(userId));

        return authorizedUsers.containsKey(userId);
    }

    public boolean userAuthpathIsGenerated() {
        return userAuthpath;
    }

    public String getTracks() {
        return tracks;
    }
}
