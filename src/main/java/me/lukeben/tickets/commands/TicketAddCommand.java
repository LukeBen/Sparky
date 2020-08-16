package me.lukeben.tickets.commands;

import me.lukeben.Sparky;
import me.lukeben.backend.Methods;
import me.lukeben.backend.commands.DiscordCommand;
import me.lukeben.Conf;
import me.lukeben.tickets.logic.Ticket;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import me.lukeben.tickets.logic.TicketManager;

public class TicketAddCommand extends DiscordCommand {

    //----------{ this command handles adding members to tickets }----------\\

    public TicketAddCommand() {
        super(Conf.BOT_INFO.PREFIX + "add");
        setDescription("Adds a user to the ticket");
    }

    @Override
    public void execute(User user, String[] args, MessageReceivedEvent event) {
        final Guild guild = Sparky.getJda().getSelfUser().getJDA().getGuildById(Conf.BOT_INFO.GUILD);
        if(args.length == 0) {
            Methods.tell(event.getTextChannel(), Methods.buildEmbed(" ", "Please see $help for valid commands.", false).build(), 5);
            return;
        }
        guild.loadMembers();
        User memberUser = event.getJDA().getUserById(args[0]);
        if(memberUser == null) {
            Methods.tell(event.getTextChannel(), Methods.buildEmbed(" ", "This is not a valid member!", false).build(), 5);
            return;
        }
        Member member = event.getGuild().getMember(memberUser);
        Role role = Sparky.getJda().getRoleById(Conf.TICKETS.TICKET_STAFF);
        Ticket currentTicket = null;
        for(Ticket ticket : TicketManager.getInstance().getOpenTickets()) {
            if(event.getTextChannel().getId().equalsIgnoreCase(ticket.getTextChannel().getId())) {
                if(event.getAuthor().getId().equalsIgnoreCase(ticket.getUser().getId()) || event.getMember().getRoles().contains(role)) {
                    currentTicket = ticket;
                }
            }
        }
        if(currentTicket == null) {
            Methods.tell(event.getTextChannel(), Methods.buildEmbed(" ", "You are not in a ticket, or you do not have permission to add people.", false).build(), 5);
            return;
        }
        Methods.tell(event.getTextChannel(), Methods.buildEmbed(" ", member.getAsMention() + " has been added to the ticket.", false).build());
        currentTicket.getTextChannel().createPermissionOverride(member).setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_WRITE, Permission.MESSAGE_READ).queue();
        TicketManager.getInstance().getOpenTickets().remove(currentTicket);
        currentTicket.getAddedUsers().add(member.getUser());
        TicketManager.getInstance().getOpenTickets().add(currentTicket);
        return;
    }

}
