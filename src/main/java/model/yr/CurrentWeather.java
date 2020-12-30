package model.yr;

import com.google.gson.annotations.SerializedName;

/**
 * A java bean to represent a current weather.
 */
public class CurrentWeather {

    // Empty constructor --> Bean
    public CurrentWeather() {}

    @SerializedName("symbol_code")
    public String symbolCode = "";

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "symbolCode='" + symbolCode + '\'' +
                '}';
    }
}
