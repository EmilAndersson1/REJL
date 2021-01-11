package model.yr;

import com.google.gson.annotations.SerializedName;

/**
 * A bean representing weather.
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
 */
public class Weather {

    public Weather() {
    }

    @SerializedName("air_temperature")
    public float temperature;
    @SerializedName("symbol_code")
    public String weatherSymbol = "";

    @Override
    public String toString() {
        return "Weather{" +
                "temperature=" + temperature +
                ", weatherSymbol='" + weatherSymbol + '\'' +
                '}';
    }
}
