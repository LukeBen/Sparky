package me.lukeben.backend.json.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.lukeben.backend.json.logic.adapters.*;
import me.lukeben.verification.utils.PurchasedResource;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.Date;

@Getter
public class SimpleConfig {

    private transient Gson gson = getGson().create();

    @Getter protected transient File file;

    public SimpleConfig(final File file) {
        this.file = file;
    }

    public SimpleConfig(final String configName) {
        this(getFile(configName));
    }

    private GsonBuilder getGson() {
        return new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .serializeNulls()
                .registerTypeAdapter(User.class, new UserTypeAdapter())
                .registerTypeAdapter(TextChannel.class, new TextChannelTypeAdapter())
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .registerTypeAdapter(PurchasedResource.class, new PurchasedResourceTypeAdapter())
                .serializeNulls()
                .enableComplexMapKeySerialization();
    }

    protected static boolean contentRequestsDefaults(final String content) {
        if (content == null) return false;
        if (content.length() == 0) return false;
        final char c = content.charAt(0);
        return c == 'd' || c == 'D';
    }

    public void load() {
        if (this.getFile().isFile()) {
            String content = DiskUtil.read(this.getFile());
            content = content.trim();
            Object toShallowLoad = null;

            if (contentRequestsDefaults(content)) {
                try {
                    toShallowLoad = this.getClass().newInstance();
                } catch (final Exception ex) {
                    ex.printStackTrace();
                    return;
                }
            } else {
                toShallowLoad = this.gson.fromJson(content, this.getClass());
            }

            Accessor.get(this.getClass()).copy(toShallowLoad, this);

        }
        save();
    }

    public void save() {
        String content = DiskUtil.read(this.getFile());
        if (contentRequestsDefaults(content)) return;
        content = this.gson.toJson(this);
        DiskUtil.write(this.getFile(), content);
    }

    public void save(String data) {
        String content = DiskUtil.read(this.getFile());
        if (contentRequestsDefaults(content)) return;
        DiskUtil.write(this.getFile(), data);

    }

    public String getCopy() {
        return this.gson.toJson(this);
    }

    private static File getFile(String fileName) {
        return new File(fileName + ".json");
    }

}