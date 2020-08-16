package me.lukeben.tickets.commands;

import me.lukeben.backend.Methods;
import me.lukeben.backend.commands.DiscordCommand;
import me.lukeben.Conf;
import me.lukeben.tickets.logic.Ticket;
import me.lukeben.tickets.logic.TicketManager;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketInfoCommand extends DiscordCommand {

    //----------{ this command handles sending info regarding the ticket }----------\\

    public TicketInfoCommand() {
        super(Conf.BOT_INFO.PREFIX + "ticketinfo");
        setDescription("Shows information about current ticket");
    }

    @Override
    public void execute(User user, String[] args, MessageReceivedEvent event) {
        Optional<Ticket> ticket = TicketManager.getInstance().getOpenTickets().stream()
                .filter(t -> t.getTextChannel().getId()
                        .equalsIgnoreCase(event.getTextChannel().getId())).findAny();
        if(!ticket.isPresent()) {
            event.getMessage().delete().queue();
            Methods.tell(event.getTextChannel(), Methods.buildEmbed(" ", "This command can only be run in a ticket!", false).build(), 5);
            return;
        }
        Methods.tell(event.getTextChannel(), Methods.buildEmbed("Ticket Info", buildDescription(ticket.get()), true).build());
    }

    public String buildDescription(Ticket ticket) {
        StringBuilder users = new StringBuilder();
        for(User user : ticket.getAddedUsers()) users.append(user.getName() + ", ");
        List<String> lines = new ArrayList<>();
        lines.add("Client` **»** " + ticket.getUser().getUser().getName());
        lines.add("Name` **»** " + ticket.getTicketName());
        lines.add("ChannelID` **»** " + ticket.getTextChannel().getId());
        lines.add("Users` **»** " + (users.toString().equalsIgnoreCase("") ? "None" : users.toString().trim()));
        lines.add("Reason` **»** " + ticket.getReason());
        StringBuilder builder = new StringBuilder();
        for(String line : lines) builder.append("`" + line + "\n");
        return builder.toString();
    }

}
