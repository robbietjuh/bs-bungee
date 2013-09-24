package net.robbytu.banjoserver.bungee.auth;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;

public class LoginCommand extends Command {
    public LoginCommand() {
        super("login");
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

        if(!AuthProvider.isRegistered(sender.getName())) {
            sender.sendMessage(ChatColor.RED + "Je moet eerst registreren voordat je kan inloggen.");
            sender.sendMessage(ChatColor.GRAY + "Gebruik /register [wachtwoord] om te registeren.");
            sender.sendMessage(" ");
            return;
        }

        if(!AuthProvider.authenticate(Main.instance.getProxy().getPlayer(sender.getName()), args[0])) {
            sender.sendMessage(ChatColor.RED + "Het wachtwoord dat je gebruikte, is incorrect.");
            return;
        }

        sender.sendMessage(ChatColor.GREEN + "Welkom terug! Je bent nu ingelogd.");

        sender.sendMessage(" ");
    }
}
