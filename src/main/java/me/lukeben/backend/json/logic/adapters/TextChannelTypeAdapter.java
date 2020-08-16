package me.lukeben.backend.json.logic.adapters;

import com.google.gson.*;
import me.lukeben.Sparky;
import net.dv8tion.jda.api.entities.TextChannel;

import java.lang.reflect.Type;

public class TextChannelTypeAdapter implements JsonSerializer<TextChannel>, JsonDeserializer<TextChannel> {

    @Override
    public TextChannel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        try {
            return Sparky.getJda().getTextChannelById(object.get("ID").getAsString());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public JsonElement serialize(TextChannel src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        try {
            object.add("ID", new JsonPrimitive(src.getId()));
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            return object;
        }
    }

}
