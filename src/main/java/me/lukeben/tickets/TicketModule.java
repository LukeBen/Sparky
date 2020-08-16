package me.lukeben.tickets;

import me.lukeben.backend.module.Module;
import me.lukeben.tickets.commands.*;

public class TicketModule extends Module {

    public TicketModule() {
        super("Tickets");
    }

    @Override
    public void moduleStart() {
        TicketData.getInstance().load();
        new TicketCommand().registerCommand();
        new TicketInfoCommand().registerCommand();
        new TicketCloseCommand().registerCommand();
        new TicketAddCommand().registerCommand();
        new TicketRemoveCommand().registerCommand();
    }

}
