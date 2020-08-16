package me.lukeben;

import lombok.Getter;
import me.lukeben.backend.json.logic.SimpleConfig;

import java.util.Date;

public class Conf extends SimpleConfig {

    public Conf() {
        super("config");
    }

    @Getter
    private transient static final Conf instance = new Conf();

    public static BotInfo BOT_INFO = new BotInfo();
    public static Tickets TICKETS = new Tickets();
    public static EmailVerification VERIFICATION = new EmailVerification();
    public static EmbedInfo EMBED = new EmbedInfo();

    public static class BotInfo {
        public static String TOKEN = "TOKEN";
        public static String PREFIX = "";
        public static String GUILD = "GUILD";
        public static String HELP_FORMAT = "`{0}` **»** {1} \n";
    }

    public static class EmailVerification {
        public static String HOST = "imap.mail.yahoo.com";
        public static String EMAIL = "example@yahoo.com";
        public static String PASSWORD = "passwrod";
        public static String FOLDER_TO_SCAN = "PayPal";
        public static Date LAST_SCANNED_DATE = null;
    }

    public static class EmbedInfo {
        public static String COLOR = "#ffd900";
        public static String EMBED_FOOTER = "Template Footer";
        public static String EMBED_THUMBNAIL = "https://cdn.discordapp.com/attachments/715183947960680519/741414661328601088/Logo_Outline.png";
    }

    public static class Tickets {
        public static int MAXIMUM_TICKETS = 2;
        public static String TICKET_STAFF = "702663981763919982";
        public static String TICKET_CATEGORY = "702663981038436452";
    }


}
