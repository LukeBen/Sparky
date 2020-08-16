package me.lukeben.backend.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordCommandListener extends ListenerAdapter {

    //----------{ handles events for command manager }----------\\

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        String[] args = event.getMessage().getContentRaw().split(" ");
        String[] commandArgs = new String[args.length - 1];
        for(int i = 1; i < args.length; i++) commandArgs[i - 1] = args[i];
        DiscordCommandManager.getInstance()
                .getCommands()
                .stream()
                .filter(cmd -> cmd.getCommand().equalsIgnoreCase(args[0]))
                .findAny()
                .ifPresent(cmd -> cmd.execute(event.getAuthor(), commandArgs, event));
    }


}
