package net.robbytu.banjoserver.bungee.tp;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;

public class TpAcceptCommand extends Command {
    public TpAcceptCommand() {
        super("tpaccept");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!TeleportUtil.acceptRequest(Main.instance.getProxy().getPlayer(sender.getName()))) sender.sendMessage(ChatColor.RED + "Kon de aanvraag niet accepteren.");
    }
}
