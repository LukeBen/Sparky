package me.lukeben.invite;

import me.lukeben.backend.Methods;
import me.lukeben.backend.commands.DiscordCommand;
import me.lukeben.Conf;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class InviteCommand extends DiscordCommand {

    //----------{ this command handles the discord invite command }----------\\

    public InviteCommand() {
        super(Conf.BOT_INFO.PREFIX + "discord");
        setDescription("Creates a discord invite.");
    }

    @Override
    public void execute(User user, String[] args, MessageReceivedEvent event) {
        Invite action = event.getTextChannel().createInvite().complete();
        Methods.tell(event.getTextChannel(), Methods.buildEmbed(" ", "Discord **»** " + action.getUrl(), false).build());
    }

}
