package net.robbytu.banjoserver.bungee.listeners;

import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.robbytu.banjoserver.bungee.Main;
import net.robbytu.banjoserver.bungee.consoles.ConsoleGateway;

public class PluginMessageListener implements Listener {
    @EventHandler
    public static void messageReceived(PluginMessageEvent event) {
        String orig_data = new String(event.getData());

        Main.instance.getLogger().warning(orig_data);

        String data_split[] = orig_data.split("/", 2);

        String tag = data_split[0];
        String data = data_split[1];

        if(tag.equals("LogGateway")) {
            String server = data.split(":", 1)[0];
            String log = data.split(":", 1)[1];

            ConsoleGateway.dispatchLog(server, log);
        }
        else if(tag.equals("ClearInventoryGlobal")) {
            String admin = data.split("/")[0];
            String user = data.split("/")[1];


            Main.instance.getLogger().warning(data);
        }
        else {
            data = new String(event.getData());

            Main.instance.getLogger().warning(data);



            Main.instance.getLogger().warning("Received incompatible bs-framework plugin command with tag " + event.getTag());
        }
    }
}
