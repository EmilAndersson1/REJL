package utils;

import java.util.Base64;

/**
 * A data processing utility class for encoding client id and secret.
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
 */
public class ClientEncoder {

    /**
     * Generates an encoded string from a client id and client secret.
     * @param clientID The client id to protect.
     * @param clientSecret The client secret to protect.
     * @return The encoded string.
     */
    public static String generate(String clientID, String clientSecret) {
        return Base64.getEncoder().encodeToString((clientID + ":" + clientSecret).getBytes());
    }
}
