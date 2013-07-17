package net.robbytu.banjoserver.bungee.donate;

import net.robbytu.banjoserver.bungee.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DonateUtil {
    public static Donation[] getDonationsForServer(String server) {
        Connection conn = Main.conn;
        ArrayList<Donation> donations = new ArrayList<Donation>();

        try {
            // Create a new select statement
            PreparedStatement statement = conn.prepareStatement("SELECT id, title, description, code, server, tag FROM bs_donations WHERE server = ?");
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
}
