package services;

import controllers.DataHandler;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

/**
 * Abstract class for all API:s services common functionality.
 */
public abstract class APIService {

    private DataHandler dataHandler;

    public APIService(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public Object apiResponse() {
        HttpResponse<JsonNode> response = response(dataHandler);
        System.out.println("CHECKPOINT api response status: " + response.getStatus());
        JSONObject jsonObject = response.getBody().getObject(); // Retrieve the parsed JSONObject from the response.
        Gson gson = new Gson(); // For marshalling.
        Object javaObject = returnObject(gson, jsonObject); // Create the java object for easy usage i the application.
        Unirest.shutDown(); // Close Unirest connection.
        return javaObject;
    }

    public abstract HttpResponse<JsonNode> response(DataHandler dataHandler);

    public abstract Object returnObject(Gson gson, JSONObject jsonObject);
}
