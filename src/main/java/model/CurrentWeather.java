package model;

/**
 * A java bean to represent a current weather.
 */
public class CurrentWeather {

    // Empty constructor --> Bean
    public CurrentWeather() {}

    // Variable name has to match corresponding json field name.
    public String symbol_code = "";

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "symbol_code='" + symbol_code + '\'' +
                '}';
    }
}
