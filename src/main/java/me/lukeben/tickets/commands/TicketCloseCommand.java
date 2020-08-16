package me.lukeben.tickets.commands;

import me.lukeben.Sparky;
import me.lukeben.backend.Methods;
import me.lukeben.backend.commands.DiscordCommand;
import me.lukeben.Conf;
import me.lukeben.tickets.logic.Ticket;
import me.lukeben.tickets.logic.TicketManager;
import me.lukeben.tickets.logic.TicketUtils;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class TicketCloseCommand extends DiscordCommand {

    //----------{ this method handles ticket closing }----------\\

    public TicketCloseCommand() {
        super(Conf.BOT_INFO.PREFIX + "close");
        setDescription("Closes the ticket");
    }

    @Override
    public void execute(User user, String[] args, MessageReceivedEvent event) {
        Role role = Sparky.getJda().getRoleById(Conf.TICKETS.TICKET_STAFF);
        for(Ticket ticket : TicketManager.getInstance().getOpenTickets()) {
            if(event.getTextChannel().getId().equalsIgnoreCase(ticket.getTextChannel().getId())) {
                if(event.getAuthor().getId().equalsIgnoreCase(ticket.getUser().getId()) || event.getMember().getRoles().contains(role)) {
                    Methods.tell(event.getTextChannel(), Methods.buildEmbed(" ", "This channel will be deleted in 60 seconds, a transcript will be sent to you afterwards!", false).build());
                    TicketUtils.closeTicket(ticket);
                    event.getTextChannel().delete().queueAfter(60, TimeUnit.SECONDS);
                }
            }
        }
    }

}
