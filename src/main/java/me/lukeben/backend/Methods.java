package me.lukeben.backend;

import lombok.experimental.UtilityClass;
import me.lukeben.Sparky;
import me.lukeben.Conf;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class Methods {

    //----------{ basic embed building method }----------\\
    public EmbedBuilder buildEmbed(String title, String description, boolean fancy) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setColor(Color.decode(Conf.EMBED.COLOR));
        eb.setDescription(description);
        if(fancy) {
            eb.setFooter(Conf.EMBED.EMBED_FOOTER);
            eb.setThumbnail(Conf.EMBED.EMBED_THUMBNAIL);
        }
        return eb;
    }

    //----------{ embed tell methods (the latter deletes the message) }----------\\
    public void tell(TextChannel channel, MessageEmbed message) {
        channel.sendMessage(message).queue();
    }

    public void tell(TextChannel channel, MessageEmbed message, int time) {
        channel.sendMessage(message).complete().delete().queueAfter(time, TimeUnit.SECONDS);
    }

    //----------{ User list serialization methods }----------\\
    public String serializeUserList(List<User> users) {
        StringBuilder builder = new StringBuilder();
        users.forEach(user -> builder.append(user.getId() + " "));
        return builder.toString();
    }

    public List<User> deserializeUserList(String users) {
        String[] userArray = users.split(" ");
        List<User> userList = new ArrayList<>();
        if(userArray.length <= 0) return userList;
        for (String s : userArray) {
            User user = Sparky.getJda().getUserById(s);
            userList.add(user);
        }
        return userList;
    }

}
