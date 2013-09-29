package net.robbytu.banjoserver.bungee.list;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;

import java.util.Map;

public class ListCommand extends Command {
    public ListCommand() {
        super("list", null, "online");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.GOLD + "Er zijn " + ChatColor.RED + Main.instance.getProxy().getOnlineCount() + ChatColor.GOLD + " van maximaal " + ChatColor.RED + ((ListenerInfo) Main.instance.getProxy().getConfigurationAdapter().getListeners().toArray()[0]).getMaxPlayers() + ChatColor.GOLD + " spelers online.");

        for(ServerInfo info : Main.instance.getProxy().getServers().values()) {
            String onlineUsers = "";
            for(ProxiedPlayer player : info.getPlayers()) onlineUsers += ((onlineUsers.equals("")) ? "" : ", ") + player.getName();
            sender.sendMessage(info.getName() + ": " + onlineUsers);
        }

        sender.sendMessage(" ");
    }
}