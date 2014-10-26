package net.robbytu.banjoserver.bungee.masks;/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class PluginsCommand extends Command {

    public PluginsCommand() {
        super("pl", null, "plugins");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        sender.sendMessage("Plugins: " + ChatColor.GREEN + "bs-bungee" + ChatColor.WHITE + ", " +
                                         ChatColor.GREEN + "bs-framework" + ChatColor.WHITE + ", " +
                                         ChatColor.GREEN + "bs-servers" + ChatColor.WHITE + ", " +
                                         ChatColor.GREEN + "bs-pvpcages" + ChatColor.WHITE + ", " +
                                         ChatColor.GREEN + "bs-scripts");
    }
}
