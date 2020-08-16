package me.lukeben.backend.commands;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DiscordCommandManager {

    //----------{ basic command manager }----------\\

    @Getter
    private static final DiscordCommandManager instance = new DiscordCommandManager();

    private final List<DiscordCommand> commands;

    public DiscordCommandManager() {
        this.commands = new ArrayList<>();
    }

}
