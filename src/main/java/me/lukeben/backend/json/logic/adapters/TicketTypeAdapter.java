package me.lukeben.backend.json.logic.adapters;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import me.lukeben.Sparky;
import me.lukeben.backend.Methods;
import me.lukeben.Conf;
import me.lukeben.tickets.logic.Ticket;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;

public class TicketTypeAdapter implements JsonSerializer<Ticket>, JsonDeserializer<Ticket> {

    private final Gson gson = getGson().create();

    private GsonBuilder getGson() {
        return new GsonBuilder()
                .serializeNulls()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .registerTypeAdapter(User.class, new UserTypeAdapter())
                .registerTypeAdapter(TextChannel.class, new TextChannelTypeAdapter())
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .enableComplexMapKeySerialization();
    }

    @Override
    public Ticket deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final Guild guild = Sparky.getJda().getSelfUser().getJDA().getGuildById(Conf.BOT_INFO.GUILD);
        JsonObject object = json.getAsJsonObject();
        try {
            Type token =  new TypeToken<List< User>>() {}.getType();
            TextChannel channel = Sparky.getJda().getTextChannelById(object.get("CHANNEL").getAsLong());
            Member member = guild.loadMembers().get().stream().filter(m -> m.getIdLong() == object.get("AUTHOR").getAsLong()).findFirst().get();
            return new Ticket(object.get("NAME").getAsString(), channel, member, object.get("REASON").getAsString(), Methods.deserializeUserList(object.get("ADDED_USERS").getAsString()));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public JsonElement serialize(Ticket src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        try {
            object.add("NAME", new JsonPrimitive(src.getTicketName()));
            object.add("CHANNEL", new JsonPrimitive(src.getTextChannel().getIdLong()));
            object.add("AUTHOR", new JsonPrimitive(src.getUser().getIdLong()));
            object.add("REASON", new JsonPrimitive(src.getReason()));
            object.add("ADDED_USERS", new JsonPrimitive(Methods.serializeUserList(src.getAddedUsers())));
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            return object;
        }
    }

}
