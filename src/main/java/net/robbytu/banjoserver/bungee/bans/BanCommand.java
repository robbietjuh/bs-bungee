package net.robbytu.banjoserver.bungee.bans;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BanCommand extends Command {
    private final String usage = "/ban [user] [reason]";

    public BanCommand() {
        super("ban");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("bs.admin")) {
            this.failCommand(sender, "You do not have permission to execute this command.");
            return;
        }

        if(args.length < 2) {
            this.failCommand(sender, "Missing arguments.");
            return;
        }

        String reasonBody = "";
        for (int i = 0; i < args.length - 1; i++) reasonBody += args[i];

        Ban ban = new Ban();
        ban.username = args[0];
        ban.reason = reasonBody;
        ban.mod = sender.getName();

        Bans.banUser(ban);

        sender.sendMessage(ChatColor.GREEN + args[0] + " is gebanned.");
    }

    private void failCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
        sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
    }
}
