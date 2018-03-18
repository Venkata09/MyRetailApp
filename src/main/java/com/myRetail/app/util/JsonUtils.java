package com.myRetail.app.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vdokku
 */


public class JsonUtils {

    protected static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    public static JsonObject generateJson(String jsonInString) {
        JsonObject jsonObject = null;
        try {
            Gson gson = new Gson();
            JsonElement element = gson.fromJson(jsonInString, JsonElement.class);
            jsonObject = element.getAsJsonObject();
        } catch (Exception ex) {
            LOGGER.debug(" Error in converting to JSON Object : " + ex);
        }


        return jsonObject;
    }

}
