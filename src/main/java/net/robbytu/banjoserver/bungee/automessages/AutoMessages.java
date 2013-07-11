package net.robbytu.banjoserver.bungee.automessages;

import net.md_5.bungee.api.ChatColor;
import net.robbytu.banjoserver.bungee.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AutoMessages {
    public static String[] broadcasts;
    public static int currentBroadcast;

    public static void broadcastNext() {
        if(currentBroadcast == broadcasts.length) {
            broadcasts = fetchBroadcasts();
            currentBroadcast = 0;
        }

        String message = broadcasts[currentBroadcast];
        Main.instance.getProxy().broadcast(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "*" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_AQUA + ChatColor.translateAlternateColorCodes("&".charAt(0), message));

        currentBroadcast++;
    }

    private static String[] fetchBroadcasts() {
        // Refresh cached broadcasts
        Main.instance.getLogger().info("Refreshing automessage cache...");

        Connection conn = Main.conn;
        ArrayList<String> messages = new ArrayList<String>();

        try {
            // Create a new select statement
            PreparedStatement statement = conn.prepareStatement("SELECT message FROM bs_broadcasts WHERE active = 1");
            ResultSet result = statement.executeQuery();

            // For each broadcast message ...
            while(result.next()) {
                messages.add(result.getString(1));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return messages.toArray(new String[messages.size()]);
    }
}
