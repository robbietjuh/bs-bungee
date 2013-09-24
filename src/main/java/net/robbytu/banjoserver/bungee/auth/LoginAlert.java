package net.robbytu.banjoserver.bungee.auth;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.robbytu.banjoserver.bungee.Main;

import java.util.concurrent.TimeUnit;

public class LoginAlert  implements Runnable {
    private ProxiedPlayer target;

    public LoginAlert() {}

    public LoginAlert(ProxiedPlayer target) {
        this.target = target;
    }

    public void run() {
        if(!AuthProvider.isAuthenticated(this.target)) {
            this.target.disconnect("Om overbelasting van onze servers te voorkomen moet je binnen 30 seconden inloggen.");
        }
    }

    public static void handle(LoginEvent event) {
        ProxiedPlayer target = Main.instance.getProxy().getPlayer(event.getConnection().getName());
        Main.instance.getProxy().getScheduler().schedule(Main.instance, new LoginAlert(target), 30, TimeUnit.SECONDS);

        boolean registered = AuthProvider.isRegistered(target.getName());

        target.sendMessage(ChatColor.GREEN + "Welkom " + ((registered) ? "terug " : "") + "in de Banjoserver, " + target.getName() + "!");
        target.sendMessage(ChatColor.GRAY + "Gebruik " + ((registered) ? ChatColor.WHITE + "/login [wachtwoord]" + ChatColor.GRAY + " om in te loggen." : ChatColor.WHITE + "/register [wachtwoord]" + ChatColor.GRAY + " om te registreren."));
        target.sendMessage(" ");
    }
}
