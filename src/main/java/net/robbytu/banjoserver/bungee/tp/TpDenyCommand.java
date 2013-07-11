package net.robbytu.banjoserver.bungee.tp;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;

public class TpDenyCommand extends Command {
    public TpDenyCommand() {
        super("tpdeny");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(TeleportUtil.denyRequest(Main.instance.getProxy().getPlayer(sender.getName()))) sender.sendMessage(ChatColor.GREEN + "Verzoek is geweigerd.");
        else sender.sendMessage(ChatColor.RED + "Kon aanvraag niet weigeren.");
    }
}
