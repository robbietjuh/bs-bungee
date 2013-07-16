package net.robbytu.banjoserver.bungee.mute;

import net.md_5.bungee.api.ChatColor;
import net.robbytu.banjoserver.bungee.Main;

import java.util.ArrayList;
import java.util.List;

public class MuteUtil {
    public static List<String> mutedPlayers = new ArrayList<String>();

    public static boolean isMuted(String player) {
        return (mutedPlayers.contains(player));
    }

    public static void unmute(String player) {
        Main.instance.getProxy().getPlayer(player).sendMessage(ChatColor.GREEN + "Je kan nu weer praten.");
        mutedPlayers.remove(player);
    }

    public static void mute(String player) {
        Main.instance.getProxy().getPlayer(player).sendMessage(ChatColor.GRAY + "Je bent gemute.");
        mutedPlayers.add(player);
    }
}
