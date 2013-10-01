package net.robbytu.banjoserver.bungee.votes;

import net.md_5.bungee.api.ChatColor;
import net.robbytu.banjoserver.bungee.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Votes {
    public static void handleVote(Vote vote) {
        Connection conn = Main.conn;

        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM bs_votes WHERE username LIKE ? AND timestamp < ?");
            statement.setString(1, vote.username);
            statement.setInt(2, (int) ((int) System.currentTimeMillis() / 1000L));
            ResultSet result = statement.executeQuery();

            boolean canRedeem = true;

            if(result.next()) {
                Main.instance.getProxy().getPlayer(vote.username).sendMessage(" ");
                Main.instance.getProxy().getPlayer(vote.username).sendMessage(ChatColor.GREEN + "Bedankt voor het stemmen op de Banjoserver!");
                Main.instance.getProxy().getPlayer(vote.username).sendMessage(" ");
                canRedeem = false;
            }

            statement = conn.prepareStatement("INSERT INTO bs_votes (username, serviceName, address, timestamp, redeemed) VALUES (?, ?, ?, ?, ?)");

            statement.setString(1, vote.username);
            statement.setString(2, vote.serviceName);
            statement.setString(3, vote.address);
            statement.setInt(4, vote.timestamp);
            statement.setInt(5, ((canRedeem == true) ? 0 : 2));

            statement.executeUpdate();

            if(canRedeem) {
                Main.instance.getProxy().getPlayer(vote.username).sendMessage(" ");
                Main.instance.getProxy().getPlayer(vote.username).sendMessage(ChatColor.GREEN + "Bedankt voor het stemmen op de Banjoserver.");
                Main.instance.getProxy().getPlayer(vote.username).sendMessage(ChatColor.GRAY + "Type nu " + ChatColor.WHITE + "/vote" + ChatColor.GRAY + " om je beloning te innen!");
                Main.instance.getProxy().getPlayer(vote.username).sendMessage(" ");
            }
        }
        catch (Exception ignored) {}
    }

    public static Vote[] openVotes() {
        return null;
    }
}
