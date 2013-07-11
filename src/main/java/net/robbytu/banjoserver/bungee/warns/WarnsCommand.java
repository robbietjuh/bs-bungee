package net.robbytu.banjoserver.bungee.warns;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;
import net.robbytu.banjoserver.bungee.bans.Ban;
import net.robbytu.banjoserver.bungee.bans.Bans;

public class WarnsCommand extends Command {
    private final String usage = "/warns [user/add [user] [warn ...]/remove [id]]";

    public WarnsCommand() {
        super("warns");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0 || args.length == 1) {
            // Show user's warns
            String user = sender.getName();
            if(args.length == 1) user = args[1];

            Warn[] warns = Warns.getUserWarns(user);
            sender.sendMessage(ChatColor.AQUA + sender.getName() + " heeft " + warns.length + " warns.");

            for(Warn warn : warns) {
                sender.sendMessage(" ");
                this.renderWarnEntry(sender, warn);
            }
        }

        if(args.length > 3 && args[0].equalsIgnoreCase("add")) {
            // Add a user warn
            if(!sender.hasPermission("bs.admin")) {
                this.failCommand(sender, "You do not have permission to execute this command.");
                return;
            }

            if(Main.instance.getProxy().getPlayer(args[2]) == null) {
                this.failCommand(sender, "The specified player seems to be offline.");
                return;
            }

            String warnBody = "";
            for (int i = 0; i < args.length - 2; i++) warnBody += args[i];

            Warn warn = new Warn();
            warn.mod = sender.getName();
            warn.server = Main.instance.getProxy().getPlayer(args[1]).getServer().getInfo().getName();
            warn.username = Main.instance.getProxy().getPlayer(args[2]).getDisplayName();
            warn.warn = warnBody;

            Warns.addUserWarn(warn);

            Main.instance.getProxy().getPlayer(args[2]).sendMessage("");
            Main.instance.getProxy().getPlayer(args[2]).sendMessage(ChatColor.GOLD + " * Je hebt een waarschuwing gekregen!");
            Main.instance.getProxy().getPlayer(args[2]).sendMessage("");
            this.renderWarnEntry(Main.instance.getProxy().getPlayer(args[2]), warn);

            sender.sendMessage("");
            sender.sendMessage(ChatColor.GREEN + "Warn was added.");
            sender.sendMessage("");
            this.renderWarnEntry(sender, warn);

            if(Warns.getUserWarns(args[1]).length >= Main.config.warns_banAt) {
                sender.sendMessage(ChatColor.GOLD + "Player got automatically banned because it has " + Main.config.warns_banAt + " warns or more.");

                Ban ban = new Ban();
                ban.username = args[1];
                ban.mod = "Systeem";
                ban.reason = Main.config.warns_banAt + " warns, waaronder: " + warn.warn;

                Bans.banUser(ban);
            }
        }

        if(args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            // Remove a user warn
            if(!sender.hasPermission("bs.admin")) {
                this.failCommand(sender, "You do not have permission to execute this command.");
                return;
            }

            Warn warn = Warns.getWarnById(Integer.getInteger(args[1]));
            if(warn != null) {
                this.renderWarnEntry(sender, warn);
                Warns.removeWarnById(Integer.getInteger(args[1]));
                sender.sendMessage(ChatColor.GREEN + "Warn has been removed.");
            }
            else {
                this.failCommand(sender, "Specified warn does not exist.");
            }
        }
    }

    private void failCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
        sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
    }

    private void renderWarnEntry(CommandSender sender, Warn warn) {
        sender.sendMessage(ChatColor.AQUA + ((warn.id != 0) ? ("#" + warn.id) : "") + " " + warn.warn);
        sender.sendMessage(ChatColor.GRAY + " - Datum:  " + warn.getFriendlyDate());
        sender.sendMessage(ChatColor.GRAY + " - Admin:  " + warn.mod);
        sender.sendMessage(ChatColor.GRAY + " - Server: " + warn.server);
    }
}
