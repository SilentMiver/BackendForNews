package org.example.configurations.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;

public class NewDTOAdapter implements JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement.isJsonPrimitive()) {
            long timestamp = jsonElement.getAsLong();
            return timestamp * 1000;
        } else {
            throw new JsonParseException("Unexpected JSON format for timestamp");
        }

    }
}
