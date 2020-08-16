package me.lukeben.tickets.logic;

import me.lukeben.tickets.TicketData;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TicketEvent extends ListenerAdapter {

    //----------{ manual check for when channels are deleted }----------\\

    @Override
    public void onTextChannelDelete(TextChannelDeleteEvent event) {
        Ticket removeTicket = null;
        for(Ticket ticket : TicketManager.getInstance().getOpenTickets()) {
            if(event.getChannel().getId().equalsIgnoreCase(event.getChannel().getId())) {
                removeTicket = ticket;
            }
        }
        if(removeTicket != null) {
            TicketManager.getInstance().getOpenTickets().remove(removeTicket);
            TicketData.getInstance().save();
        }
    }

}
