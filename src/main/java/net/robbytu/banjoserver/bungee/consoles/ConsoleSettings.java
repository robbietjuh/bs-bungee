package net.robbytu.banjoserver.bungee.consoles;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class ConsoleSettings {
    public static List<ProxiedPlayer> listeningPlayers = new ArrayList<ProxiedPlayer>();

    public static void turnOn(ProxiedPlayer player) {
        player.sendMessage(ChatColor.GREEN + "Enabled console snooping");
        if(!isListening(player)) listeningPlayers.add(player);
    }

    public static void turnOff(ProxiedPlayer player) {
        player.sendMessage(ChatColor.GREEN + "Disabled console snooping");
        if(isListening(player)) listeningPlayers.remove(player);
    }

    public static boolean isListening(ProxiedPlayer player) {
        return listeningPlayers.contains(player);
    }
}
