package net.robbytu.banjoserver.bungee.masks;/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class AboutCommand extends Command {

    public AboutCommand() {
        super("about");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.GRAY + "Banjoserver 2.0");
        sender.sendMessage(ChatColor.GRAY + " * Meer informatie op www.banjoserver.nl");
        sender.sendMessage(" ");
    }
}
