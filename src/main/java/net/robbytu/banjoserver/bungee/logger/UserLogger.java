package net.robbytu.banjoserver.bungee.logger;

import net.md_5.bungee.api.event.LoginEvent;
import net.robbytu.banjoserver.bungee.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserLogger {
    public static void logLogin(final LoginEvent event) {
        final Connection conn = Main.conn;

        Main.instance.getProxy().getScheduler().runAsync(Main.instance, new Runnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement statement = conn.prepareStatement("INSERT INTO bs_logins (username, ip, login_time) VALUES (?, ?, ?)");

                    statement.setString(1, event.getConnection().getName());
                    statement.setString(2, event.getConnection().getAddress().getAddress().toString());
                    statement.setInt(3, (int) (System.currentTimeMillis() / 1000L));

                    statement.executeUpdate();
                }
                catch(Exception ignored) {}
            }
        });
    }
}
