package net.robbytu.banjoserver.bungee.consoles;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;
import net.robbytu.banjoserver.bungee.perms.Permissions;

public class ConsoleCommand extends Command {
    private final String usage = "/console [on/off]";

    public ConsoleCommand() {
        super("console");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!Permissions.hasPermission(sender.getName(), "bs.bungee.console")) {
            this.failCommand(sender, "You do not have permission to execute this command.");
            return;
        }

        if(args.length != 1) {
            this.failCommand(sender, "Need at least 1 argument.");
            return;
        }

        if(!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off")) {
            this.failCommand(sender, "Unknown argument: " + args[0]);
            return;
        }

        if(args[0].equalsIgnoreCase("on")) {
            ConsoleSettings.turnOn(Main.instance.getProxy().getPlayer(sender.getName()));
            sender.sendMessage(ChatColor.GRAY + "Turned on Consoles");
        }

        if(args[0].equalsIgnoreCase("off")) {
            ConsoleSettings.turnOff(Main.instance.getProxy().getPlayer(sender.getName()));
            sender.sendMessage(ChatColor.GRAY + "Turned off Consoles");
        }
    }

    private void failCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
        sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
    }
}
