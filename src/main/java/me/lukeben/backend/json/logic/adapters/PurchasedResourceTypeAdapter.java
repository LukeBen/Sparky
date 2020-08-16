package me.lukeben.backend.json.logic.adapters;

import com.google.gson.*;
import me.lukeben.verification.utils.PurchasedResource;
import me.lukeben.verification.utils.ResourceType;

import java.lang.reflect.Type;
import java.util.Date;

public class PurchasedResourceTypeAdapter implements JsonSerializer<PurchasedResource>, JsonDeserializer<PurchasedResource> {

    @Override
    public PurchasedResource deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        try {
            Date date = new Date(object.get("DATE").getAsLong());
            ResourceType type = ResourceType.valueOf(object.get("RESOURCE").getAsString());
            return new PurchasedResource(date, type);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public JsonElement serialize(PurchasedResource src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        try {
            object.add("DATE", new JsonPrimitive(src.getDate().getTime()));
            object.add("RESOURCE", new JsonPrimitive(src.getType().name()));
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            return object;
        }
    }

}
