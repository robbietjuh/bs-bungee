package net.robbytu.banjoserver.bungee.bans;

import net.robbytu.banjoserver.bungee.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Bans {
    public static void banUser(Ban ban) {
        // Insert User warn
        try {
            Connection conn = Main.conn;
            PreparedStatement statement = conn.prepareStatement("INSERT INTO bs_bans (user, mod, reason, date, server, active) VALUES (?, ?, ?, ?, ?, 1)");

            statement.setString(1, ban.username);
            statement.setString(2, ban.mod);
            statement.setString(3, ban.reason);
            statement.setInt(4, ban.date);
            statement.setString(5, ban.server);

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
