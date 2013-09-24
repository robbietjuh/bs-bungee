package net.robbytu.banjoserver.bungee.auth;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;

public class RegisterCommand extends Command {
    public RegisterCommand() {
        super("register");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(AuthProvider.isAuthenticated(Main.instance.getProxy().getPlayer(sender.getName()))) {
            sender.sendMessage(ChatColor.GRAY + "Je bent al ingelogd.");
            return;
        }

        if(args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Geef een wachtwoord op.");
            return;
        }

        if(AuthProvider.isRegistered(sender.getName())) {
            sender.sendMessage(ChatColor.RED + "Dit account is al geregistreerd.");
            sender.sendMessage(ChatColor.GRAY + "Gebruik /login [wachtwoord] om in te loggen.");
            sender.sendMessage(" ");
            return;
        }

        AuthProvider.register(Main.instance.getProxy().getPlayer(sender.getName()), args[0]);

        sender.sendMessage(ChatColor.GREEN + "Welkom op de Banjoserver! Je bent nu ingelogd.");
        sender.sendMessage(" ");
    }
}