package net.robbytu.banjoserver.bungee.listeners;

import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.robbytu.banjoserver.bungee.Main;
import net.robbytu.banjoserver.bungee.consoles.ConsoleGateway;

public class PluginMessageListener implements Listener {
    @EventHandler
    public static void messageReceived(PluginMessageEvent event) {
        if(event.getTag().equals("LogGateway")) {
            String data = new String(event.getData());

            String server = data.split(":", 1)[0];
            String log = data.split(":", 1)[1];

            ConsoleGateway.dispatchLog(server, log);
        }
        else {
            Main.instance.getLogger().warning("Received incompatible bs-framework plugin command with tag " + event.getTag());
        }
    }
}
