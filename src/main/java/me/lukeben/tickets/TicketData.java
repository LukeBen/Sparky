package me.lukeben.tickets;

import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import me.lukeben.backend.json.logic.DiskUtil;
import me.lukeben.backend.json.logic.PersistentFile;
import me.lukeben.tickets.logic.Ticket;
import me.lukeben.tickets.logic.TicketManager;

import java.io.File;
import java.util.List;

public class TicketData extends PersistentFile {

    @Getter
    private transient static final TicketData instance = new TicketData();

    File file = getFile(true, "data");

    @Override
    public void load() {
        try {
            Object o = gson.fromJson(DiskUtil.read(file), new TypeToken<List<Ticket>>() {}.getType());
            if(o != null) {
                List<Ticket> tickets = (List<Ticket>) o;
                tickets.forEach(t -> TicketManager.getInstance().getOpenTickets().add(t));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try {
            save(true, TicketManager.getInstance().getOpenTickets(), file);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
