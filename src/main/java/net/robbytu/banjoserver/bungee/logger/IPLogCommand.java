package net.robbytu.banjoserver.bungee.logger;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class IPLogCommand extends Command {
    private final String usage = "/ids-for-ip [ip]";

    public IPLogCommand() {
        super("ids-for-ip", null, "iplog");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            this.failCommand(sender, "Missing arguments.");
            return;
        }

        if(!sender.hasPermission("bs.admin")) {
            this.failCommand(sender, "You don't have enough permissions to execute this command.");
            return;
        }

        final Connection conn = Main.conn;

        try {
            PreparedStatement statement = conn.prepareStatement("SELECT DISTINCT(username), login_time FROM bs_logins WHERE ip = ? ORDER BY id DESC");
            statement.setString(1, '/' + args[0]);
            ResultSet result = statement.executeQuery();

            sender.sendMessage(ChatColor.YELLOW + "Volgende users zijn ingelogd geweest op " + args[0]);
            while(result.next()) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
                String date = sdf.format(result.getInt(1));

                sender.sendMessage(ChatColor.GRAY + " * " + ChatColor.WHITE + result.getString(1) + ChatColor.GRAY + " (laatste login " + date + ")");
            }
        }
        catch(Exception ignored) {
            sender.sendMessage(ChatColor.RED + "Informatie kon niet worden opgehaald. Databasefout.");
        }
    }

    private void failCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
        sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
    }
}
