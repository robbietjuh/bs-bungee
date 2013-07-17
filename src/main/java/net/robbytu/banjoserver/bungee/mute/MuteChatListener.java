package net.robbytu.banjoserver.bungee.mute;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.robbytu.banjoserver.bungee.Main;

public class MuteChatListener implements Listener {
    @EventHandler
    public static void handleChat(ChatEvent event) {
        ProxiedPlayer sender = null;

        for(ProxiedPlayer player : Main.instance.getProxy().getPlayers()) {
            if(player.getAddress().equals(event.getSender().getAddress())) {
                sender = player;
            }
        }

        if(MuteUtil.isMuted(sender.getName())) {
            sender.sendMessage(ChatColor.RED + "Je bent gemute.");
            event.setCancelled(true);
        }
    }
}