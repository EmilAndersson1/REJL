package utils;

import java.util.Base64;

public class ClientEncoder {
    public static String generate(String clientID, String clientSecret) {
        return Base64.getEncoder().encodeToString((clientID + ":" + clientSecret).getBytes());
    }
}
