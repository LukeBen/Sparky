package me.lukeben.verification;


import me.lukeben.Sparky;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EmailTask {

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void callAsynchronousTask() {
        scheduler.scheduleAtFixedRate(() -> Sparky.getInstance().getReader().scanEmails(), 0, 10, TimeUnit.MINUTES);
    }

}
