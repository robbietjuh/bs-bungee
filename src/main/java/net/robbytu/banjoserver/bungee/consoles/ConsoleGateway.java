package net.robbytu.banjoserver.bungee.consoles;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ConsoleGateway {
    public static void dispatchLog(String server, String log) {
        for(ProxiedPlayer player : (ProxiedPlayer[]) ConsoleSettings.getListeners()) {
            player.sendMessage(ChatColor.DARK_GRAY + "[" + server + "] " + ChatColor.GRAY + "" + log);
        }
    }
}
