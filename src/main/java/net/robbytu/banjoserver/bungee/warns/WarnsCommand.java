package net.robbytu.banjoserver.bungee.warns;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
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
            if(args.length == 1) user = args[0];

            Warn[] warns = Warns.getUserWarns(user, false);
            Ban[] bans = Bans.getUserBans(user, false);
            sender.sendMessage(ChatColor.GOLD + user + " heeft " + warns.length + " warns en is " + bans.length + " keer gebanned.");

            for(Warn warn : warns) {
                sender.sendMessage(" ");
                this.renderWarnEntry(sender, warn);
            }

            for(Ban ban : bans) {
               sender.sendMessage(" ");
               this.renderBanEntry(sender, ban);
            }
        }

        if(args.length >= 3 && args[0].equalsIgnoreCase("add")) {
            // Add a user warn
            if(!sender.hasPermission("bs.admin")) {
                this.failCommand(sender, "You do not have permission to execute this command.");
                return;
            }

            if(Main.instance.getProxy().getPlayer(args[1]) == null) {
                this.failCommand(sender, "The specified player seems to be offline.");
                return;
            }

            String warnBody = "";
            for (int i = 2; i < args.length; i++) warnBody += ((warnBody.equals("")) ? "" : " ") + args[i];

            Warn warn = new Warn();
            warn.mod = sender.getName();
            warn.server = Main.instance.getProxy().getPlayer(args[1]).getServer().getInfo().getName();
            warn.username = Main.instance.getProxy().getPlayer(args[1]).getDisplayName();
            warn.warn = warnBody;

            Warns.addUserWarn(warn);

            Main.instance.getProxy().getPlayer(args[1]).sendMessage("");
            Main.instance.getProxy().getPlayer(args[1]).sendMessage(ChatColor.GOLD + " * Je hebt een waarschuwing gekregen!");
            Main.instance.getProxy().getPlayer(args[1]).sendMessage("");
            this.renderWarnEntry(Main.instance.getProxy().getPlayer(args[1]), warn);

            if(Warns.getUserWarns(args[1], true).length >= Main.config.warns_banAt) {
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

            Warn warn = Warns.getWarnById(args[1]);
            if(warn != null) {
                sender.sendMessage("");

                warn.active = false;
                this.renderWarnEntry(sender, warn);

                sender.sendMessage("");

                Warns.removeWarnById(args[1]);
                sender.sendMessage(ChatColor.GREEN + "Warn has been set to inactive.");

                if(Main.instance.getProxy().getPlayer(warn.username) != null) {
                    ProxiedPlayer target = Main.instance.getProxy().getPlayer(warn.username);
                    target.sendMessage("");
                    target.sendMessage(ChatColor.DARK_GRAY + " [" + ChatColor.DARK_RED + "*" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " Een van je warns is ingetrokken!");
                    target.sendMessage("");
                    this.renderWarnEntry(target, warn);
                    target.sendMessage("");
                }

                sender.sendMessage("");
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
        if(warn.active) {
            sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Warn:" + ChatColor.RESET + " " + ChatColor.AQUA + warn.warn);
            sender.sendMessage(ChatColor.DARK_GRAY + ((warn.id != 0) ? ("(" + warn.id + ") ") : "") + ChatColor.GRAY + "Op " + ChatColor.BOLD + warn.getFriendlyDate() + ChatColor.RESET + ChatColor.GRAY + " door " + ChatColor.BOLD + warn.mod + ChatColor.RESET + ChatColor.GRAY + " in " + ChatColor.BOLD + warn.server);
        }
        else {
            sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.STRIKETHROUGH + "Warn: " + warn.warn);
            sender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + ((warn.id != 0) ? ("(" + warn.id + ") ") : "") + "Op " + warn.getFriendlyDate() + " door " + warn.mod + " in " + warn.server);
        }
    }

    private void renderBanEntry(CommandSender sender, Ban ban) {
        sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Ban:" + ChatColor.RESET + " " + ChatColor.AQUA + ban.reason);
        sender.sendMessage(ChatColor.GRAY + "Op " + ChatColor.BOLD + ban.getFriendlyDate() + ChatColor.RESET + ChatColor.GRAY + " door " + ChatColor.BOLD + ban.mod + ChatColor.RESET + ChatColor.GRAY + ". " + ((ban.active) ? ChatColor.BOLD + "Actief" : "Niet meer actief"));
    }
}
