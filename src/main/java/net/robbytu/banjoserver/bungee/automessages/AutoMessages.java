package net.robbytu.banjoserver.bungee.automessages;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.config.ListenerInfo;
import net.robbytu.banjoserver.bungee.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AutoMessages {
    public static String[] broadcasts = new String[0];
    public static int currentBroadcast;
    public static boolean lastOnline = false;

    public static void broadcastNext() {
        if(currentBroadcast == broadcasts.length) {
            broadcasts = fetchBroadcasts();
            currentBroadcast = 0;
        }

        String message = "";
        if(lastOnline) message = ChatColor.translateAlternateColorCodes("&".charAt(0), broadcasts[currentBroadcast]);
        else message = ChatColor.GOLD + "" + Main.instance.getProxy().getPlayers().size() + "/" + ((ListenerInfo)Main.instance.getProxy().getConfigurationAdapter().getListeners().toArray()[0]).getMaxPlayers() + " online";

        Main.instance.getProxy().broadcast(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "*" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_AQUA + message);

        if(lastOnline) currentBroadcast++;
        lastOnline = !lastOnline;
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
