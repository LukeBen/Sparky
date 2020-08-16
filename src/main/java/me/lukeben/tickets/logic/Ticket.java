package me.lukeben.tickets.logic;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

@AllArgsConstructor
@Data
public class Ticket {

    //----------{ basic ticket object }----------\\

    private String ticketName;
    private TextChannel textChannel;
    private Member user;
    private String reason;
    private List<User> addedUsers;

}
