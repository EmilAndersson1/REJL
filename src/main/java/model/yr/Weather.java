package model.yr;

import com.google.gson.annotations.SerializedName;

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
