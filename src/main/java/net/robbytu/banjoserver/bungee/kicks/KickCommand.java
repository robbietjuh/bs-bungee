package net.robbytu.banjoserver.bungee.kicks;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;
import net.robbytu.banjoserver.bungee.perms.Permissions;

public class KickCommand extends Command {
    private final String usage = "/kick [user] [reason]";

    public KickCommand() {
        super("kick");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!Permissions.hasPermission(sender.getName(), "bs.bungee.kick")) {
            this.failCommand(sender, "You do not have permission to execute this command.");
            return;
        }

        if(args.length < 2) {
            this.failCommand(sender, "Missing arguments.");
            return;
        }

        if(Main.instance.getProxy().getPlayer(args[0]) == null) {
            this.failCommand(sender, "The specified player seems to be offline.");
            return;
        }

//        if(Permissions.hasPermission(args[0], "bs.bungee.kick")) {
//            this.failCommand(sender, "You cannot kick this user.");
//            return;
//        }

        String reasonBody = "";
        for (int i = 1; i < args.length; i++) reasonBody += (reasonBody.equals("") ? "" : " ") + args[i];

        Main.instance.getProxy().getPlayer(args[0]).disconnect(ChatColor.RED + "" + ChatColor.BOLD + "Je werd gekicked:\n\n" + ChatColor.WHITE + reasonBody);

        for(ProxiedPlayer player : Main.instance.getProxy().getPlayers()) {
            if(Permissions.hasPermission(player.getName(), "bs.bungee.kick.receive_broadcast")) {
                player.sendMessage("");
                player.sendMessage(ChatColor.RED + sender.getName() + " heeft " + args[0] + " gekicked:");
                player.sendMessage(ChatColor.RED + " * " + reasonBody);
                player.sendMessage("");
            }
        }

        Main.instance.getLogger().info("User " + args[0] + " got kicked by " + sender.getName() + ": " + reasonBody);
    }

    private void failCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
        sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
    }
}
