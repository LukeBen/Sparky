package me.lukeben.administration;

import me.lukeben.Sparky;
import me.lukeben.backend.Methods;
import me.lukeben.backend.commands.DiscordCommand;
import me.lukeben.Conf;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class EmbedCommand extends DiscordCommand {

    //----------{ This command allows admins to create nice looking embeds }----------\\

    public EmbedCommand() {
        super(Conf.BOT_INFO.PREFIX + "embed");
    }

    //embed <title> <description>

    @Override
    public void execute(User user, String[] args, MessageReceivedEvent event) {
        Role role = Sparky.getJda().getRoleById(Conf.TICKETS.TICKET_STAFF);
        event.getMessage().delete().queue();
        if(event.getMember().getRoles().isEmpty() || !event.getMember().getRoles().contains(role)) {
            Methods.tell(event.getTextChannel(), Methods.buildEmbed(" ", "You do not have permission to use this command", false).build(), 5);
            return;
        }
        if(args.length < 2) {
            Methods.tell(event.getTextChannel(), Methods.buildEmbed(" ", "Please use $embed <title> <description>!", false).build(), 5);
            return;
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i < args.length; i++) builder.append(args[i] + " ");
        String title = args[0].replace("_", " ");
        String description = builder.toString().trim();
        Methods.tell(event.getTextChannel(), Methods.buildEmbed(title, description, true).build());
    }

}
