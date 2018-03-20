package com.myRetail.app.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.myRetail.app.model.Price;

import java.io.IOException;

/**
 * @author vdokku
 */
public class PriceDeserializer extends JsonDeserializer<Price> {


    @Override
    public Price deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String EMPTY_STRING = "";

        JsonNode jsonNode = jsonParser.readValueAsTree();
        String productId = jsonNode.has("productId") ? jsonNode.get("productId").asText() : EMPTY_STRING;
        String price = jsonNode.has("price") ? jsonNode.get("price").asText() : EMPTY_STRING;
        String currency = jsonNode.has("currency") ? jsonNode.get("currency").asText() : EMPTY_STRING;

        return new Price(productId, price, currency);
    }
}
