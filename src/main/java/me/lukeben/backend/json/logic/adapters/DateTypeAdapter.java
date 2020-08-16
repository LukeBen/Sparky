package me.lukeben.backend.json.logic.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

public class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {


    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        try {
            return new Date(object.get("DATE").getAsLong());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        try {
            object.add("DATE", new JsonPrimitive(src.getTime()));
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            return object;
        }
    }
}
