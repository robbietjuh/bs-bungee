package net.robbytu.banjoserver.bungee.listeners;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;

import net.md_5.bungee.event.EventHandler;
import net.robbytu.banjoserver.bungee.Main;
import net.robbytu.banjoserver.bungee.auth.LoginAlert;
import net.robbytu.banjoserver.bungee.bans.BanLoginListener;
import net.robbytu.banjoserver.bungee.consoles.ConsoleGateway;
import net.robbytu.banjoserver.bungee.consoles.ConsoleSettings;
import net.robbytu.banjoserver.bungee.logger.UserLogger;

public class LoginListener implements Listener {
    @EventHandler
    public static void login(LoginEvent event) {
        if(!event.isCancelled()) BanLoginListener.handle(event);
        if(!event.isCancelled()) LoginAlert.handle(event);
        if(!event.isCancelled()) UserLogger.logLogin(event);
    }
}
