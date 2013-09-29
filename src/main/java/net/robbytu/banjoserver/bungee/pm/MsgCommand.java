package net.robbytu.banjoserver.bungee.pm;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;

public class MsgCommand extends Command {
    public MsgCommand() {
        super("msg", null, "tell", "t", "pm", "whisper");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Geef een gebruikersnaam en bericht op.");
            return;
        }

        if(Main.instance.getProxy().getPlayer(args[0]) == null) {
            sender.sendMessage(ChatColor.RED + args[0] + " is offline.");
            sender.sendMessage(ChatColor.GRAY + "Je kan nog wel een mail sturen met het /mail commando.");
            return;
        }

        String message = "";
        for (int i = 1; i < args.length; i++) message += ((message.equals("")) ? "" : " ") + args[i];

        sender.sendMessage(ChatColor.GRAY + "[" + sender.getName() + " -> " + args[0] + "] " + message);
        Main.instance.getProxy().getPlayer(args[0]).sendMessage(ChatColor.GRAY + "[" + sender.getName() + " -> " + args[0] + "] " + message);
    }
}
