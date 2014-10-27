package net.robbytu.banjoserver.bungee.bans;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;
import net.robbytu.banjoserver.bungee.perms.Permissions;

public class IPBanCommand extends Command {
    private final String usage = "/ipban [user/ip] [reason]";

    public IPBanCommand() {
        super("ipban", null, "banip");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!Permissions.hasPermission(sender.getName(), "bs.bungee.ipban")) {
            this.failCommand(sender, "You do not have permission to execute this command.");
            return;
        }

        if(args.length < 2) {
            this.failCommand(sender, "Missing arguments.");
            return;
        }

        if(Main.instance.getProxy().getPlayer(args[0]) != null) args[0] = Main.instance.getProxy().getPlayer(args[0]).getAddress().getAddress().toString();

        String reasonBody = "";
        for (int i = 1; i < args.length; i++) reasonBody += (reasonBody.equals("") ? "" : " " ) + args[i];

        Ban ban = new Ban();
        ban.username = (args[0].substring(0, 1).equals("/") ? "" : "/") + args[0];
        ban.reason = "IP ban: " + reasonBody;
        ban.mod = sender.getName();

        Bans.banUser(ban);

        for(ProxiedPlayer player : Main.instance.getProxy().getPlayers()) {
            if(player.getAddress().getAddress().toString().equalsIgnoreCase(args[0])) {
                Ban playerBan = new Ban();
                ban.username = player.getName();
                ban.reason = "IP ban: " + reasonBody;
                ban.mod = sender.getName();

                Bans.banUser(playerBan);
            }
        }
    }

    private void failCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
        sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
    }
}
