package net.robbytu.banjoserver.bungee.tp;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;
import net.robbytu.banjoserver.bungee.perms.Permissions;

public class TpHereCommand extends Command {
    private final String usage = "/tphere [user]";

    public TpHereCommand() {
        super("tphere");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            this.failCommand(sender, "Missing arguments.");
            return;
        }

        if(Main.instance.getProxy().getPlayer(args[0]) == null) {
            this.failCommand(sender, args[0] + " is offline.");
            return;
        }

        if(sender.getName().equalsIgnoreCase(args[0])) {
            this.failCommand(sender, "Je kan niet naar jezelf teleporteren...");
            return;
        }

        if(!Permissions.hasPermission(sender.getName(), "bs.bungee.tphere")) {
            sender.sendMessage(ChatColor.GRAY + "Je hebt geen permissies om deze actie uit te voeren.");
        }
        else {
            TeleportUtil.requests.put(Main.instance.getProxy().getPlayer(args[0]), Main.instance.getProxy().getPlayer(sender.getName()));
            TeleportUtil.targets.put(Main.instance.getProxy().getPlayer(sender.getName()), Main.instance.getProxy().getPlayer(args[0]));

            TeleportUtil.acceptRequest(Main.instance.getProxy().getPlayer(sender.getName()), true);
        }
    }

    private void failCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
        sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
    }
}
