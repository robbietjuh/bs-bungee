package net.robbytu.banjoserver.bungee.servers;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;
import net.robbytu.banjoserver.bungee.PluginMessager;

public class ServerCommand extends Command {
    public ServerCommand() {
        super("server", null, "servers", "warp");
    }

    public void execute(CommandSender sender, String[] args) {
        String arg = "";
        for(String a : args) arg += a;

        // Pass on to bs-framework
        PluginMessager.sendMessage(Main.instance.getProxy().getPlayer(sender.getName()).getServer(), "userCommand", sender.getName(), "server" + (arg.equals("") ? "" : " " + arg));
    }
}
