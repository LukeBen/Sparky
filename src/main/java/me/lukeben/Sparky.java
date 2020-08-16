package me.lukeben;

import lombok.Getter;
import me.lukeben.administration.AdministrationModule;
import me.lukeben.backend.commands.DiscordCommandListener;
import me.lukeben.backend.module.ModuleController;
import me.lukeben.invite.InviteModule;
import me.lukeben.tickets.TicketModule;
import me.lukeben.tickets.logic.TicketEvent;
import me.lukeben.verification.EmailTask;
import me.lukeben.verification.VerificationModule;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import me.lukeben.verification.EmailReader;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

@Getter
public class Sparky extends ListenerAdapter {

    @Getter private static Sparky instance;
    @Getter public static JDA jda;

    private EmailReader reader;

    public Sparky() throws LoginException {
        //----------{ main instance }----------\\
        instance = this;
        //----------{ configuration file }----------\\
        Conf.getInstance().load();
        reader = new EmailReader();
        //----------{ bot registration }----------\\
        new EmailTask().callAsynchronousTask();
        jda =  JDABuilder.createDefault(Conf.BOT_INFO.TOKEN)
            .enableIntents(GatewayIntent.GUILD_MEMBERS)
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .addEventListeners(new DiscordCommandListener())
            .addEventListeners(new TicketEvent())
            .setActivity(Activity.of(Activity.ActivityType.DEFAULT, "https://docs.immortalplugins.com"))
            .build();
        //----------{ member loading }----------\\
        jda.getGuilds().forEach(g -> g.loadMembers().onSuccess(e -> registerModules()));
    }

    public static void main(String[] args) throws LoginException { ;
        new Sparky();
    }

    public void registerModules() {
        Arrays.asList(
                new VerificationModule(),
                new TicketModule(),
                new InviteModule(),
                new AdministrationModule()
        ).forEach(ModuleController::moduleStart);
    }

}
