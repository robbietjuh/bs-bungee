package net.robbytu.banjoserver.bungee.warns;

import net.robbytu.banjoserver.bungee.Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Warns {
    public static Warn[] getUserWarns(String username) {
        Connection conn = Main.conn;
        ArrayList<Warn> warns = new ArrayList<Warn>();

        try {
            // Create a new select statement
            Statement select = conn.createStatement();
            ResultSet result = select.executeQuery("SELECT id, user, mod, warn, date, server FROM bs_warns WHERE username = '" + username + "'");

            // For each server ...
            while(result.next()) {
                // Create a new Server instance
                Warn warn = new Warn();

                // Fill in properties
                warn.id = result.getInt(1);
                warn.username = result.getString(2);
                warn.mod = result.getString(3);
                warn.warn = result.getString(4);
                warn.date = result.getInt(5);
                warn.server = result.getString(6);

                // Add server to return array
                warns.add(warn);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the array of servers
        return warns.toArray(new Warn[warns.size()]);
    }
}
