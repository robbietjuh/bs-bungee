package net.robbytu.banjoserver.bungee.donate;

import net.robbytu.banjoserver.bungee.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DonateUtil {
    public static HashMap<String, String> prices = new HashMap<String, String>();

    public static Donation[] getDonationsForServer(String server) {
        Connection conn = Main.conn;
        ArrayList<Donation> donations = new ArrayList<Donation>();

        try {
            // Create a new select statement
            PreparedStatement statement = conn.prepareStatement("SELECT id, title, description, code, server, tag, expires FROM bs_donations WHERE server = ?");
            statement.setString(1, server);
            ResultSet result = statement.executeQuery();

            // For each donation option ...
            while(result.next()) {
                // Create a new Donation instance
                Donation donation = new Donation();

                // Fill in properties
                donation.id = result.getInt(1);
                donation.title = result.getString(2);
                donation.description = result.getString(3);
                donation.code = result.getString(4);
                donation.server = result.getString(5);
                donation.tag = result.getString(6);
                donation.expires = result.getInt(7);

                // Add donation to return array
                donations.add(donation);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the array of donations
        return donations.toArray(new Donation[donations.size()]);
    }

    public static void populateStatics() {
        Connection conn = Main.conn;

        try {
            PreparedStatement statement = conn.prepareStatement("SELECT keyword, price FROM bs_keywords");
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                // Populate statics
                prices.put(result.getString(1), result.getString(2));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void processSuccesfulDonation(String username, Donation donation) {
        Connection conn = Main.conn;
        ArrayList<String> tasks = new ArrayList<String>();

        try {
            // Create a new select statement
            PreparedStatement statement = conn.prepareStatement("SELECT task FROM bs_donation_tasks WHERE donation = ? AND action = 1");
            statement.setInt(1, donation.id);
            ResultSet result = statement.executeQuery();

            // For each donation task ...
            while(result.next()) {
                tasks.add(result.getString(1).replaceAll("$USER", username));
            }

            // Add them to the task queue
            for(String task : tasks) {
                statement = conn.prepareStatement("INSERT INTO bs_tasks (tasktype, task, server) VALUES (?, ?, ?)");

                statement.setInt(1, 1);
                statement.setString(2, task);
                statement.setString(3, donation.server);

                statement.executeUpdate();
            }

            // Add donation to database
            statement = conn.prepareStatement("INSERT INTO bs_donators (username, donation, date, expires, expired) VALUES (?, ?, ?, ?, ?)");

            statement.setString(1, username);
            statement.setInt(2, donation.id);
            statement.setInt(3, (int) (System.currentTimeMillis() / 1000L));
            statement.setInt(4, ((donation.expires > 0) ? ((int) (System.currentTimeMillis() / 1000L)) + donation.expires : 0));
            statement.setInt(5, 0);

            statement.executeUpdate();

        } catch (Exception ignored) {}
    }
}
