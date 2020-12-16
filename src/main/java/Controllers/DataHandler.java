package Controllers;

import model.CurrentWeather;

/**
 * Takes some weather data and creates //TODO: a Spotify object. Something real.
 */
public class DataHandler {

    // Currently just for testing.
    public String getMoodFromWeather(CurrentWeather weather) {

        String mood = "Valance ";

        if ( (weather.symbol_code.contains("rain")) || (weather.symbol_code.contains("cloud") )) {
            mood += "< 0.5\n:(";
        } else {
            mood += ">= 0.5\n:)";
        }

        mood += "\n";

        return mood;
    }

}
