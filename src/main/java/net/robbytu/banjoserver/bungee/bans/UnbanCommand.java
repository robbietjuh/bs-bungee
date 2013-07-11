package net.robbytu.banjoserver.bungee.bans;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class UnbanCommand extends Command {
    private final String usage = "/unban [username]";

    public UnbanCommand() {
        super("unban");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("bs.admin")) {
            this.failCommand(sender, "You do not have permission to execute this command.");
            return;
        }

        if(args.length != 1) {
            this.failCommand(sender, "Missing arguments.");
            return;
        }

        Ban[] bans = Bans.getUserBans(args[0], true);
        for (Ban ban : bans) {
            ban.active = false;
            Bans.updateUserBan(ban);
        }

        sender.sendMessage(ChatColor.GREEN + "Blokkade opgeheven: " + args[0]);
    }

    private void failCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
        sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
    }
}