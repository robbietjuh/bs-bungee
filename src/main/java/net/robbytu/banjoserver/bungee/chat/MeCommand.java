package net.robbytu.banjoserver.bungee.chat;/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;
import net.robbytu.banjoserver.bungee.mute.MuteUtil;

public class MeCommand extends Command {

    public MeCommand() {
        super("me", null);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(MuteUtil.isMuted(sender.getName())) return;

        if(args.length == 0) return;

        String action = "";
        for (int i = 1; i < args.length; i++) action += ((i == 0) ? "" : " ") + args[i];

        for(ProxiedPlayer player : Main.instance.getProxy().getPlayers())
            player.sendMessage(ChatColor.YELLOW + " * " + sender.getName() + " " + action);
    }
}
