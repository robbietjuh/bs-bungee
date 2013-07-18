package net.robbytu.banjoserver.bungee.auth;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class AuthProvider {
    private static List<ProxiedPlayer> authenticatedUsers = new ArrayList<ProxiedPlayer>();

    public static boolean isAuthenticated(ProxiedPlayer player) {
        return (authenticatedUsers.contains(player));
    }

    public static boolean authenticate(ProxiedPlayer player, String providedPassword) {
        // Todo
        return false;
    }

    public static void unauthorizePlayer(ProxiedPlayer player) {
        authenticatedUsers.remove(player);
    }

    public static boolean isRegistered(String username) {
        // Todo
        return false;
    }
}
