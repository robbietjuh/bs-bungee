package net.robbytu.banjoserver.bungee.tp;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;

public class TpCommand extends Command {
    private final String usage = "/tp [user]";

    public TpCommand() {
        super("tp", null, "tpa", "call");
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

        if(TeleportUtil.isAwaitingApproval(Main.instance.getProxy().getPlayer(sender.getName()), Main.instance.getProxy().getPlayer(args[0]))) {
            this.failCommand(sender, "Je hebt " + args[0] + " al een verzoek gestuurd.");
            return;
        }

        if(!sender.hasPermission("bs.admin")) {
            TeleportUtil.requestTeleport(Main.instance.getProxy().getPlayer(sender.getName()), Main.instance.getProxy().getPlayer(args[0]));
            sender.sendMessage(ChatColor.GRAY + "Er is een verzoek verstuurd naar " + args[0] + "...");
        }
        else {
            TeleportUtil.requests.put(Main.instance.getProxy().getPlayer(sender.getName()), Main.instance.getProxy().getPlayer(args[0]));
            TeleportUtil.targets.put(Main.instance.getProxy().getPlayer(args[0]), Main.instance.getProxy().getPlayer(sender.getName()));

            TeleportUtil.acceptRequest(Main.instance.getProxy().getPlayer(args[0]), true);

            sender.sendMessage(ChatColor.GRAY + "Teleporteren...");
        }
    }

    private void failCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
        sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
    }
}
