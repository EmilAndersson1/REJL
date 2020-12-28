package utils;

public class MoodInterpreter {

    /*
     * Ratings are subjectively mapped based on the developers collective
     * and completely biased experiences.
     * To make more accurate music predictions is out of the scope of the assignment.
     * This could be improved in the future.
     *
     * https://community.spotify.com/t5/Content-Questions/Valence-as-a-measure-of-happiness/td-p/4385221
     */
    public static float weatherToValance(String weather) {
        float valance;

        switch (weather) {
            case "clearsky":
                valance = 0.900f;
                break;
            case "cloudy":
            case "fog":
                valance = 0.550f;
                break;
            case "fair":
                valance = 0.750f;
                break;
            case "heavyrain":
            case "heavyrainandthunder":
            case "heavyrainshowers":
            case "heavyrainshowersandthunder":
            case "snowandthunderr":
            case "rain":
            case "rainandthunder":
            case "rainshowers":
            case "rainshowersandthunder":
                valance = 0.300f;
                break;
            case "heavysleet":
            case "heavysleetandthunder":
            case "heavysleetshowersandthundert":
                valance = 0.200f;
                break;
            case "heavysnow":
            case "heavysnowandthunder":
            case "heavysnowshowers":
            case "heavysnowshowersandthunder":
            case "snowshowersandthunder":
            case "lightsnow":
            case "lightsnowandthunder":
            case "lightsnowshowers":
            case "lightssleetshowersandthunder":
            case "lightssnowshowersandthunder":
                valance = 0.400f;
                break;
            case "lightrain":
            case "lightrainandthunder":
            case "lightrainshowers":
            case "lightrainshowersandthunder":
                valance = 0.450f;
                break;
            case "lightsleet":
            case "lightsleetandthunder":
            case "lightsleetshowers":
            case "sleet":
            case "sleetandthunder":
            case "sleetshowers":
            case "sleetshowersandthunder":
                valance = 0.350f;
                break;
            case "partlycloudy":
            case "snow":
                valance = 0.800f;
                break;
            case "snowshowers":
                valance = 0.700f;
                break;

            default:
                valance = 0.500f; // Average valance.
//                //TODO: Exception ?
//                throw new IllegalStateException("Unexpected value: " + weather);
        }
        return valance;
    }

}
