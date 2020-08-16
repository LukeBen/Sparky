package me.lukeben.verification;

import me.lukeben.Sparky;
import me.lukeben.backend.Methods;
import me.lukeben.backend.commands.DiscordCommand;
import me.lukeben.Conf;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class VerifyCommand extends DiscordCommand {

    public VerifyCommand() {
        super(Conf.BOT_INFO.PREFIX + "verify");
        setDescription("Verifies a client");
    }

    @Override
    public void execute(User user, String[] args, MessageReceivedEvent event) {
        final Guild guild = Sparky.getJda().getSelfUser().getJDA().getGuildById(Conf.BOT_INFO.GUILD);
        if(args.length == 0) {
            Methods.tell(event.getTextChannel(), Methods.buildEmbed(" ", "Please see $help for valid commands.", false).build(), 5);
            return;
        }
        String email = args[0];
        System.out.println(email);
        System.out.println(VerificationManager.getInstance().getPendingVerification());
        if(VerificationManager.getInstance().getPendingVerification().containsKey(email)) {
            System.out.println(VerificationManager.getInstance().getPendingVerification().get(email));
        }
    }

}
