package net.robbytu.banjoserver.bungee.auth;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.robbytu.banjoserver.bungee.Main;
import net.robbytu.banjoserver.bungee.directsupport.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        Connection conn = Main.conn;

        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM bs_auth WHERE username = ?");
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            return (result.next());
        }
        catch(Exception ignored) {}

        return false;
    }
}
