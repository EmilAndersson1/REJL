package model.yr;

import com.google.gson.annotations.SerializedName;

/**
 * A java bean to represent current weather.
 */
public class CurrentWeather {

    // Empty constructor --> Bean
    public CurrentWeather() {}

    @SerializedName("symbol_code")
    public String weatherSymbolCode = "";

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "weatherSymbolCode='" + weatherSymbolCode + '\'' +
                '}';
    }
}
