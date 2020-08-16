package me.lukeben;

import me.lukeben.backend.Methods;
import me.lukeben.backend.commands.DiscordCommand;

import me.lukeben.backend.commands.DiscordCommandManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends DiscordCommand {

    //----------{ this is the basic help command }----------\\

    public HelpCommand() {
        super(Conf.BOT_INFO.PREFIX + "help");
    }

    @Override
    public void execute(User user, String[] args, MessageReceivedEvent event) {
        EmbedBuilder eb = Methods.buildEmbed("ImmortalPlugins Help", "", true);
        Methods.tell(event.getTextChannel(), buildHelp(eb).build());

    }

    public EmbedBuilder buildHelp(EmbedBuilder eb) {
        StringBuilder builder = new StringBuilder();
        DiscordCommandManager.getInstance().getCommands().forEach(cmd -> {
            builder.append(Conf.BOT_INFO.HELP_FORMAT.replace("{0}", cmd.getCommand()).replace("{1}", cmd.getDescription()));
        });
        eb.addField(" ", builder.toString(), false);
        return eb;
    }

}
