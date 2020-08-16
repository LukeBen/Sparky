package me.lukeben.backend.commands;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Getter @Setter
public abstract class DiscordCommand {

    //----------{ abstract class all commands extend }----------\\

    private final String command;
    private String description;

    public DiscordCommand(String command) {
        this.command = command;
        this.description = "";
    }

    public void execute(User user, String[] args, MessageReceivedEvent event) {

    }

    public void registerCommand() {
        DiscordCommandManager.getInstance().getCommands().add(this);
    }

}
