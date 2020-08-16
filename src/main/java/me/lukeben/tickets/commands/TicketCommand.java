package me.lukeben.tickets.commands;

import me.lukeben.backend.Methods;
import me.lukeben.backend.commands.DiscordCommand;
import me.lukeben.Conf;
import me.lukeben.tickets.TicketData;
import me.lukeben.tickets.logic.Ticket;
import me.lukeben.tickets.logic.TicketManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class TicketCommand extends DiscordCommand {

    //----------{ this command handles ticket creation }----------\\

    public TicketCommand() {
        super(Conf.BOT_INFO.PREFIX + "ticket");
        setDescription("Creates a ticket");
    }

    @Override
    public void execute(User user, String[] args, MessageReceivedEvent event) {
        int openTickets = 0;
        for(Ticket ticket : TicketManager.getInstance().getOpenTickets()) {
            if(ticket.getUser().getId().equals(event.getAuthor().getId())) {
                openTickets++;
            }
        }
        if(openTickets >= Conf.TICKETS.MAXIMUM_TICKETS) {
            event.getMessage().delete().queue();
            Methods.tell(event.getTextChannel(), Methods.buildEmbed(" ", "You already have reached the maximum capacity for open tickets (" + Conf.TICKETS.MAXIMUM_TICKETS + ")", false).build(), 5);
            return;
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < args.length; i++) builder.append(args[i] + " ");
        String name = user.getName() + " (#" + TicketManager.getInstance().getOpenTickets().size() + ")";
        TextChannel channel = event.getGuild().getCategoryById(Conf.TICKETS.TICKET_CATEGORY).createTextChannel(name).complete();
        channel.createPermissionOverride(event.getMember()).setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_WRITE, Permission.MESSAGE_READ).queue();
        Methods.tell(channel, Methods.buildEmbed("Ticket Opened By : " + user.getName(), "Thank you for opening a ticket, LukeBen will be with you shortly \n **Reason** : " + builder.toString().trim(), true).build());
        Ticket ticket = new Ticket(name, channel, event.getMember(), builder.toString().trim(), new ArrayList<>());
        TicketManager.getInstance().getOpenTickets().add(ticket);
        event.getChannel().sendMessage(Methods.buildEmbed(" ", "Opening a ticket for you " + channel.getAsMention() + "!", false).build()).queue();
        TicketData.getInstance().saveAll();
    }

}
