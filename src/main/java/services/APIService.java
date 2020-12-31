package services;

import controll.Controller;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

/**
 * Abstract class for all API:s services common functionality.
 */
public abstract class APIService {

    private Controller controller;

    public APIService(Controller controller) {
        this.controller = controller;
    }

    public Object apiResponse() {
        HttpResponse<JsonNode> response = jsonResponse(controller);
        System.out.println("CHECKPOINT api response status: " + response.getStatus());
        JSONObject jsonObject = response.getBody().getObject(); // Retrieve the parsed JSONObject from the response.
        Gson gson = new Gson(); // For marshalling.
        Object javaObject = convertJsonResponseToJava(gson, jsonObject);
        Unirest.shutDown(); // Close Unirest connection.
        System.out.println("CHECKPOINT api response java Object: \n" + javaObject.toString());
        return javaObject;
    }

    public abstract HttpResponse<JsonNode> jsonResponse(Controller controller);

    public abstract Object convertJsonResponseToJava(Gson gson, JSONObject jsonObject);
}
