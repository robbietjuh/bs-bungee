package net.robbytu.banjoserver.bungee.mail;

import net.robbytu.banjoserver.bungee.Main;
import net.robbytu.banjoserver.bungee.directsupport.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Mails {
    public static Mail[] getMailForUser(String user, int page) {
        Connection conn = Main.conn;
        ArrayList<Mail> mails = new ArrayList<Mail>();

        try {
            // Create a new select statement
            PreparedStatement statement = conn.prepareStatement("SELECT id, from_user, to_user, date, message, unread FROM bs_mail WHERE to_user = ? AND removed = 0 LIMIT " + (10*(page+1)) + ",10");
            statement.setString(1, user);
            ResultSet result = statement.executeQuery();

            // For each mail ...
            while(result.next()) {
                // Create a new Mail instance
                Mail mail = new Mail();

                // Fill in properties
                mail.id = result.getInt(1);
                mail.from_user = result.getString(2);
                mail.to_user = result.getString(3);
                mail.date = result.getInt(4);
                mail.message = result.getString(5);
                mail.setUnread(result.getInt(6));

                // Add mail to return array
                mails.add(mail);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the array of tickets
        return mails.toArray(new Mail[mails.size()]);
    }

    public static void sendMail(Mail mail) {
        Connection conn = Main.conn;

        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO bs_mail (from_user, to_user, date, message, unread, removed) VALUES (?, ?, ?, ?, ?, 0)");

            statement.setString(1, mail.from_user);
            statement.setString(2, mail.to_user);
            statement.setInt(3, mail.date);
            statement.setString(4, mail.message);
            statement.setInt(5, (mail.unread) ? 1 : 0);

            statement.executeUpdate();
        } catch (Exception ignored) {}
    }

    public static void updateMail(Mail mail) {
        // Update Mail object in database
        try {
            Connection conn = Main.conn;
            PreparedStatement statement = conn.prepareStatement("UPDATE bs_mail SET from_user = ?, to_user = ?, date = ?, message = ?, unread = ? WHERE id = ? AND removed = 0");

            statement.setString(1, mail.from_user);
            statement.setString(2, mail.to_user);
            statement.setInt(3, mail.date);
            statement.setString(4, mail.message);
            statement.setInt(5, (mail.unread) ? 1 : 0);
            statement.setInt(6, mail.id);

            statement.executeUpdate();
        }
        catch (Exception ignored) {}
    }

    public static void clearMailboxForUser(String name) {
        // Update everything but DO NOT delete it, just in case we need to get some logs
        try {
            Connection conn = Main.conn;

            PreparedStatement statement = conn.prepareStatement("UPDATE bs_mail SET removed = 1 WHERE to_user = ?");
            statement.setString(1, name);
            statement.executeUpdate();
        }
        catch(Exception ignored) {}
    }
}
