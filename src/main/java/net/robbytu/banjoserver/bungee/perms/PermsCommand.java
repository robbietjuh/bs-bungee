package net.robbytu.banjoserver.bungee.perms;/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class PermsCommand extends Command {
private final String usage = "/perms [user] [server] [world] [permission]";

        public PermsCommand() {
            super("perms");
        }

        @Override
        public void execute(CommandSender sender, String[] args) {
            if(!Permissions.hasPermission(sender.getName(), "bs.bungee.perms")) {
                this.failCommand(sender, "You do not have permission to execute this command.");
                return;
            }

            if(args.length != 4) {
                this.failCommand(sender, "Insufficient parameters");
                return;
            }

            if(Permissions.getPermissionEntry(args[0], args[3], args[1], args[2]) == null)
                sender.sendMessage(ChatColor.RED + "Permissions denied for " + args[3]);
            else
                sender.sendMessage(ChatColor.GREEN + "Permission granted for " + args[3]);
        }

        private void failCommand(CommandSender sender, String message) {
            sender.sendMessage(ChatColor.RED + message);
            sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
        }
}
