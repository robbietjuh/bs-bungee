package net.robbytu.banjoserver.bungee.gamemode;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class GamemodeCommand extends Command {
    public GamemodeCommand() {
        super("gamemode");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.RED + "Dit commando is uitgeschakeld om problemen die zich voorheen hebben voorgedaan, te voorkomen. Maak gebruik van het " + ChatColor.GOLD + "/gm" + ChatColor.RED + " commando.");
    }
}
