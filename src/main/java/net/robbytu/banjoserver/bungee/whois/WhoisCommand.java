package net.robbytu.banjoserver.bungee.whois;/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;
import net.robbytu.banjoserver.bungee.auth.AuthProvider;
import net.robbytu.banjoserver.bungee.directsupport.Tickets;
import net.robbytu.banjoserver.bungee.mute.MuteUtil;
import net.robbytu.banjoserver.bungee.perms.Permissions;

import java.util.Arrays;

public class WhoisCommand extends Command {
    private String usage = "/whois [user]";

    public WhoisCommand() {
        super("whois", null, "user");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!Permissions.hasPermission(sender.getName(), "bs.bungee.whois")) {
            this.failCommand(sender, "You do not have permission to execute this command.");
            return;
        }

        if(args.length < 1) {
            this.failCommand(sender, "Missing arguments.");
            return;
        }

        sender.sendMessage(" ");
        ProxiedPlayer player = Main.instance.getProxy().getPlayer(args[0]);

        String authStatus = ChatColor.RED + "-";
        String ipAddress = ChatColor.RED + "-";
        String serverName = ChatColor.RED + "-";
        String mutedStatus = ChatColor.RED + "-";
        String ticketStatus = ChatColor.RED + "-";

        if(player == null) sender.sendMessage(ChatColor.YELLOW + " * Warning: " + args[0] + " is offline.");
        else {
            sender.sendMessage(ChatColor.YELLOW + "About " + args[0]);

            authStatus = (AuthProvider.isAuthenticated(player)) ?
                    ChatColor.GREEN + "Authenticated" :
                    ChatColor.RED + "Not authenticated";

            ipAddress = ChatColor.WHITE + "" + player.getAddress().getAddress().toString();

            serverName = ChatColor.WHITE + "" + player.getServer().getInfo().getName();

            mutedStatus = (MuteUtil.isMuted(args[0])) ?
                    ChatColor.RED + "Yes" :
                    ChatColor.GREEN + "No";

            ticketStatus = (Tickets.inTicket(args[0])) ?
                    "One-to-one Ticket chat" :
                    "Public chat";
        }

        sender.sendMessage(ChatColor.DARK_GRAY + " * " + ChatColor.GRAY + "Auth status: " + authStatus);
        sender.sendMessage(ChatColor.DARK_GRAY + " * " + ChatColor.GRAY + "IP address:  " + ipAddress);
        sender.sendMessage(ChatColor.DARK_GRAY + " * " + ChatColor.GRAY + "Server:       " + serverName);
        sender.sendMessage(ChatColor.DARK_GRAY + " * " + ChatColor.GRAY + "Muted:         " + mutedStatus);
        sender.sendMessage(ChatColor.DARK_GRAY + " * " + ChatColor.GRAY + "Chat:           " + ticketStatus);
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.YELLOW + "Further information might be collected through:");
        sender.sendMessage(ChatColor.DARK_GRAY + " * " + ChatColor.GRAY + "/users-for-ip " + ipAddress);
        sender.sendMessage(ChatColor.DARK_GRAY + " * " + ChatColor.GRAY + "/ips-for-user " + args[0]);
        sender.sendMessage(ChatColor.DARK_GRAY + " * " + ChatColor.GRAY + "/warns " + args[0]);
        sender.sendMessage(ChatColor.DARK_GRAY + " * " + ChatColor.GRAY + "/perms " + args[0]); /* TODO */
        sender.sendMessage(ChatColor.DARK_GRAY + " * " + ChatColor.GRAY + "/votes " + args[0]); /* TODO */
        sender.sendMessage(" ");
    }

    private void failCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
        sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
    }
}
