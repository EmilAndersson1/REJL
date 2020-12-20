package services;

import Controllers.DataHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

        // Retrieve the parsed JSONObject from the response.
        JsonNode jsonNode = response.getBody();
        JSONObject jsonObject = jsonNode.getObject();

        // Gson instance for marshalling and unmarshalling.
        GsonBuilder builder = new GsonBuilder();
        Gson gson           = builder.create();

        // Create the java object for easy usage i the application.
        Object javaObject = returnObject(gson, jsonObject);

        // Close Unirest connection.
        Unirest.shutDown();

        System.out.println(javaObject); // Prints out the java object after instantiation.

        return javaObject;
    }

    public abstract HttpResponse<JsonNode> response(DataHandler dataHandler);

    public abstract Object returnObject(Gson gson, JSONObject jsonObject);
}
