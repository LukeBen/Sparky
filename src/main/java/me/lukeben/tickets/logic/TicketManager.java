package me.lukeben.tickets.logic;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TicketManager {

    //----------{ handles local ticket data }----------\\

    @Getter
    private static final TicketManager instance = new TicketManager();

    private final List<Ticket> openTickets;

    public TicketManager() {
        this.openTickets = new ArrayList<>();
    }

}
