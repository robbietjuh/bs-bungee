package net.robbytu.banjoserver.bungee.consoles;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ConsoleCommand extends Command {
    private final String usage = "/console [on/off] [server]";

    public ConsoleCommand() {
        super("console");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("bs.admin")) {
            this.failCommand(sender, "You do not have permission to execute this command.");
            return;
        }

        if(args.length == 0) {
            sender.sendMessage(" ");
            sender.sendMessage(ChatColor.YELLOW + "Your current settings:");
            for(String[] settings : ConsoleSettings.getSettings(sender.getName().toLowerCase())) {
                sender.sendMessage(ChatColor.GRAY + " * " + ChatColor.WHITE + "" + settings[0] + ": " + ((settings[1].equalsIgnoreCase("on")) ? ChatColor.GREEN + "Listening" : ChatColor.RED + "Muted"));
            }
            sender.sendMessage(" ");
        }

        if(args.length != 2) {
            this.failCommand(sender, "Need at least 2 arguments.");
            return;
        }

        if(!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off")) {
            this.failCommand(sender, "Unknown argument: " + args[0]);
            return;
        }

        if(args[0].equalsIgnoreCase("on")) {
            ConsoleSettings.turnOn(sender.getName().toLowerCase(), args[1]);
            sender.sendMessage(ChatColor.GRAY + "Turned on Console for server " + args[1]);
        }

        if(args[0].equalsIgnoreCase("off")) {
            ConsoleSettings.turnOff(sender.getName().toLowerCase(), args[1]);
            sender.sendMessage(ChatColor.GRAY + "Turned off Console for server " + args[1]);
        }
    }

    private void failCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
        sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
    }
}
