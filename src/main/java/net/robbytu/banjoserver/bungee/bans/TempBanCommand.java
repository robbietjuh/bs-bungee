package net.robbytu.banjoserver.bungee.bans;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.perms.Permissions;

public class TempBanCommand extends Command {
    private final String usage = "/tempban [user] [time in sec.] [reason]";

    public TempBanCommand() {
        super("tempban");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!Permissions.hasPermission(sender.getName(), "bs.bungee.tempban")) {
            this.failCommand(sender, "You do not have permission to execute this command.");
            return;
        }

        if(args.length < 3) {
            this.failCommand(sender, "Missing arguments.");
            return;
        }

        if(Permissions.hasPermission(args[0], "bs.bungee.tempban")) {
            this.failCommand(sender, "You cannot ban this user.");
            return;
        }

        String reasonBody = "Tempban (" + args[1] + " sec.):";
        for (int i = 2; i < args.length; i++) reasonBody += " " + args[i];

        Ban ban = new Ban();
        ban.username = args[0];
        ban.reason = reasonBody;
        ban.mod = sender.getName();
        ban.tempban = ban.date + Integer.parseInt(args[1]);

        Bans.banUser(ban);
    }

    private void failCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
        sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
    }
}
