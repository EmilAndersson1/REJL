package utils;

import com.google.gson.Gson;
import model.yr.Weather;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeComparator {

    public static String findCurrentWeatherTime(Weather weather) {
        String timeNow = dateNowFormat();
        for (int i = 0; i < 5; i++) {
            if (timeNow.substring(11, 13).equals(weather.weatherTimes[i].time.substring(11, 13))) {
                System.out.println(weather.weatherTimes[i]);
                return new Gson().toJson(weather.weatherTimes[i]);
            }
        }
        return "No matching time. Try again.";
    }

    private static String dateNowFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
