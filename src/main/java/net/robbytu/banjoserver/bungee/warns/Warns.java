package net.robbytu.banjoserver.bungee.warns;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.robbytu.banjoserver.bungee.Main;

import java.sql.*;
import java.util.ArrayList;

public class Warns {
    public static Warn[] getUserWarns(String username) {
        Connection conn = Main.conn;
        ArrayList<Warn> warns = new ArrayList<Warn>();

        try {
            // Create a new select statement
            PreparedStatement statement = conn.prepareStatement("SELECT id, user, mod, warn, date, server FROM bs_warns WHERE username LIKE ?");
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            // For each warn ...
            while(result.next()) {
                // Create a new Warn instance
                Warn warn = new Warn();

                // Fill in properties
                warn.id = result.getInt(1);
                warn.username = result.getString(2);
                warn.mod = result.getString(3);
                warn.warn = result.getString(4);
                warn.date = result.getInt(5);
                warn.server = result.getString(6);

                // Add warn to return array
                warns.add(warn);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the array of warns
        return warns.toArray(new Warn[warns.size()]);
    }

    public static void addUserWarn(Warn warn) {
        // Insert User warn
        try {
            Connection conn = Main.conn;
            PreparedStatement statement = conn.prepareStatement("INSERT INTO bs_warns (user, mod, warn, date, server) VALUES (?, ?, ?, ?, ?)");

            statement.setString(1, warn.username);
            statement.setString(2, warn.mod);
            statement.setString(3, warn.warn);
            statement.setInt(4, warn.date);
            statement.setString(5, warn.server);

            statement.executeUpdate();

            for(ProxiedPlayer player : Main.instance.getProxy().getPlayers()) {
                if(player.hasPermission("bs.admin")) {
                    player.sendMessage("");
                    player.sendMessage(ChatColor.GOLD + warn.mod + " heeft " + warn.username + " een warn gegeven in " + warn.server + ":");
                    player.sendMessage(ChatColor.GOLD + " * " + warn.warn);
                    player.sendMessage("");
                }
            }

            Main.instance.getLogger().info("User " + warn.username + " got warned by " + warn.mod + ": " + warn.warn);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Warn getWarnById(int id) {
        // Get warn by ID
        try {
            Connection conn = Main.conn;
            PreparedStatement statement = conn.prepareStatement("SELECT id, user, mod, warn, date, server FROM bs_warns WHERE id = ?");

            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            if(result.next()) {
                // Create a new Warn instance
                Warn warn = new Warn();

                // Fill in properties
                warn.id = result.getInt(1);
                warn.username = result.getString(2);
                warn.mod = result.getString(3);
                warn.warn = result.getString(4);
                warn.date = result.getInt(5);
                warn.server = result.getString(6);

                return warn;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void removeWarnById(int id) {
        try {
            Connection conn = Main.conn;
            PreparedStatement statement = conn.prepareStatement("DELETE FROM bs_warns WHERE id = ?");

            statement.setInt(1, id);

            statement.executeUpdate();

            Main.instance.getLogger().info("Warn #" + id + " got removed");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
