package me.lukeben.tickets.logic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lukeben.backend.Methods;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketUtils {

    //----------{ handles closing tickets and making transcripts }----------\\

    private static String createTicketTranscript(String transcript) {
        Gson gson = new Gson();
        URL url = null;
        try {
            url = new URL("https://hastebin.com/documents");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            connection.setDoOutput(true);
            connection.getOutputStream().write(transcript.getBytes(Charset.defaultCharset()));
            JsonObject object = gson.fromJson(new InputStreamReader(connection.getInputStream()), JsonObject.class);
            String url2 = "https://hastebin.com/" + object.get("key").getAsString();
            return url2;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "null";
    }

    public static String closeTicket(Ticket ticket) {
        StringBuilder builder = new StringBuilder();
        builder.append(getTime(ticket.getTextChannel().getTimeCreated()) + " Ticket Transcript: " + ticket.getTicketName() + "\n\n");
        List<Message> messages = new ArrayList<>();
        ticket.getTextChannel().getHistoryFromBeginning(100).queue(messageHistory -> messageHistory
                .getRetrievedHistory().stream().filter(message -> !message.getAuthor().isBot())
                .forEach(message -> messages.add(message)));
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                Collections.reverse(messages);
                messages.forEach(message -> builder.append("[" + getTime(message.getTimeCreated()) + "] " + message.getAuthor().getName() + " : " + message.getContentRaw() + "\n"));
                PrivateChannel channel = ticket.getUser().getUser().openPrivateChannel().complete();;
                if(channel == null) return;
                channel.sendMessage(Methods.buildEmbed(" ", "Transcript **»** " + createTicketTranscript(builder.toString()), false).build()).queue();
            }
        }, 3000);
        return "";
    }

    public static String getTime(OffsetDateTime time) {
        return time.getMonthValue() + "/" + time.getDayOfMonth() + "/" + time.getYear();
    }


}
