package me.lukeben.backend.json.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.lukeben.backend.json.logic.adapters.*;
import me.lukeben.tickets.logic.Ticket;
import me.lukeben.verification.utils.PurchasedResource;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class PersistentFile {

    private static List<PersistentFile> files = new ArrayList<>();

    protected PersistentFile() {
        files.add(this);
    }

    // ----------------------------------------
    // GSON
    // ----------------------------------------

    protected final Gson gson = buildGson().create();


    // ----------------------------------------
    // File
    // ----------------------------------------

    private GsonBuilder buildGson() {
        return new GsonBuilder()
                .serializeNulls()
                .enableComplexMapKeySerialization()
                .registerTypeAdapter(User.class, new UserTypeAdapter())
                .registerTypeAdapter(TextChannel.class, new TextChannelTypeAdapter())
                .registerTypeAdapter(Ticket.class, new TicketTypeAdapter())
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .registerTypeAdapter(PurchasedResource.class, new PurchasedResourceTypeAdapter())
                .serializeNulls()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation();
    }

    protected File getFile(boolean data, String name) {

        if (data) {
            File dataFolder = new File("data");
            dataFolder.mkdirs();
            return new File(dataFolder , name + ".json");
        }

        return new File(name + ".json");
    }

    // ----------------------------------------
    // Saving
    // ----------------------------------------

    public void save(boolean data, Object toSave, String name) {
        save(data, toSave, getFile(data, name));
    }

    public void save(boolean data, Object toSave, File file) {
        DiskUtil.write(file, gson.toJson(toSave));
    }

    // ----------------------------------------
    // Abstract methods.
    // ----------------------------------------

    public abstract void load();

    public abstract void save();

    // ----------------------------------------
    // Mass saving.
    // ----------------------------------------

    public static void saveAll() {
        files.forEach(PersistentFile::save);
    }

}
