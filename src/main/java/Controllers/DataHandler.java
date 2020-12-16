package Controllers;

import model.Location;

/**
 * Takes some weather data and creates a Spotify object.
 */
public class DataHandler {

    public String getMoodFromWeather(Location location) {

        if (location.coordinates[0]==-16.516667 && location.coordinates[1]==-68.166667) {
            return "Happy";
        } else {
            return "Sad";
        }
    }

}
