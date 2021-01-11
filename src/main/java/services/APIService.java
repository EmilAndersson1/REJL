package services;

import controll.Controller;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

/**
 * API service to act as a http client to external API servers.
 *
 * @author Leo Mellberg Holm, Emil Andersson, Joakim Tell, Robert Rosencrantz.
 */
public abstract class APIService {

    private Controller controller;

    public APIService(Controller controller) {
        this.controller = controller;
    }

    /**
     * Retrieve a java bean representation from a API call.
     * @return The java bean.
     */
    public Object apiResponse() {
        HttpResponse<JsonNode> response = jsonResponse(controller);
        JSONObject jsonObject = response.getBody().getObject();
        Gson gson = new Gson();
        Object javaObject = convertJsonResponseToJava(gson, jsonObject);
        Unirest.shutDown();
        return javaObject;
    }

    /**
     * Retrieve http response from a API call.
     * @param controller The server controller.
     * @return The http response.
     */
    public abstract HttpResponse<JsonNode> jsonResponse(Controller controller);

    /**
     * Transform a JSON object with Gson to a java representation.
     * @param gson The Gson object.
     * @param jsonObject The Json object.
     * @return A java bean.
     */
    public abstract Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject);
}
