package me.lukeben.backend.json.logic.adapters;

import com.google.gson.*;
import me.lukeben.Sparky;
import net.dv8tion.jda.api.entities.User;

import java.lang.reflect.Type;

public class UserTypeAdapter implements JsonSerializer<User>, JsonDeserializer<User> {

    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        try {
            return Sparky.getJda().getUserById(object.get("ID").getAsLong());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public JsonElement serialize(User user, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        try {
            object.add("ID", new JsonPrimitive(user.getIdLong()));
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            return object;
        }
    }

}
