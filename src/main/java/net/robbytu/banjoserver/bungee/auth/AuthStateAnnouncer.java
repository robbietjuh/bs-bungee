package net.robbytu.banjoserver.bungee.auth;

import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.robbytu.banjoserver.bungee.Main;
import net.robbytu.banjoserver.bungee.PluginMessager;

public class AuthStateAnnouncer implements Listener {
    @EventHandler
    public static void handle(ServerConnectedEvent event) {
        if(event.getServer().getInfo().getName().equalsIgnoreCase("hub")) {
            Main.instance.getLogger().info("Detected server switch to hub. Sending authentication data.");
            PluginMessager.sendMessage(event.getServer(), "PlayerAuthInfo", event.getPlayer().getName(), (AuthProvider.isAuthenticated(event.getPlayer())) ? "authenticated" : "unauthorized");
        }
    }
}
