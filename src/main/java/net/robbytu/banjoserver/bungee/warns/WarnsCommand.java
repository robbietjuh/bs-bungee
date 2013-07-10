package net.robbytu.banjoserver.bungee.warns;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class WarnsCommand extends Command {
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
                sender.sendMessage(ChatColor.AQUA + "#" + warn.id + " " + warn.warn);
                sender.sendMessage(ChatColor.GRAY + " - Datum:  " + warn.getFriendlyDate());
                sender.sendMessage(ChatColor.GRAY + " - Admin:  " + warn.mod);
                sender.sendMessage(ChatColor.GRAY + " - Server: " + warn.server);
            }
        }
    }

}
