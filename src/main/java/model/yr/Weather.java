package model.yr;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Weather {

    public Weather() {
    }

    @SerializedName("timeseries")
    public WeatherTimes[] weathers = null;

    @Override
    public String toString() {
        return "Weather{" +
                "weathers=" + Arrays.toString(weathers) +
                '}';
    }

    public class WeatherTimes {
        public WeatherTimes() {}
        public String time = "";
        @SerializedName("data")
        public WeatherData weatherData;

        @Override
        public String toString() {
            return "WeatherTimes{" +
                    "time='" + time + '\'' +
                    ", weatherData=" + weatherData +
                    '}';
        }
    }

    class WeatherData {
        public WeatherData() {}
        @SerializedName("instant")
        public Instant instant = null;
        @SerializedName("next_1_hours")
        public OneHour oneHour = null;

        @Override
        public String toString() {
            return "WeatherData{" +
                    "instant=" + instant +
                    ", oneHour=" + oneHour +
                    '}';
        }
    }

    class Instant {
        public Instant() {}
        @SerializedName("details")
        public Details details = null;

        @Override
        public String toString() {
            return "Instant{" +
                    "details=" + details +
                    '}';
        }
    }

    class OneHour {
        public OneHour() {}
        @SerializedName("summary")
        public Summary summary = null;

        @Override
        public String toString() {
            return "OneHour{" +
                    "summary=" + summary +
                    '}';
        }
    }

    class Summary {
        public Summary() {}
        @SerializedName("symbol_code")
        public String symbolCode = "";

        @Override
        public String toString() {
            return "Summary{" +
                    "symbolCode='" + symbolCode + '\'' +
                    '}';
        }
    }

    class Details {
        public Details() {}
        @SerializedName("air_temperature")
        public float temperature;

        @Override
        public String toString() {
            return "Details{" +
                    "temperature=" + temperature +
                    '}';
        }
    }
}
