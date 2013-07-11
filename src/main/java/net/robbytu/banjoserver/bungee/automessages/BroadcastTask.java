package net.robbytu.banjoserver.bungee.automessages;

import net.robbytu.banjoserver.bungee.Main;

import java.util.concurrent.TimeUnit;

public class BroadcastTask implements Runnable {

    public void run() {
        AutoMessages.broadcastNext();
        Main.instance.getProxy().getScheduler().schedule(Main.instance, new BroadcastTask(), 300, TimeUnit.SECONDS);
    }
}
