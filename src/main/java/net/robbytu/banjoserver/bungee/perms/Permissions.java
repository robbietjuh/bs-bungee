package net.robbytu.banjoserver.bungee.perms;/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */

import net.robbytu.banjoserver.bungee.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Permissions {
    public static String getPrefixForUser(String user) {
        Connection conn = Main.conn;

        try {
            // Create a new select statement
            PreparedStatement statement = conn.prepareStatement("SELECT `bs_perm_groups`.`prefix` " +
                                                                "FROM `bs_perm_groups` " +
                                                                "INNER JOIN `bs_perm_members` ON `bs_perm_members`.`group` = `bs_perm_groups`.`id`" +
                                                                "WHERE bs_perm_members.user LIKE ? " +
                                                                "LIMIT 1");
            statement.setString(1, user);
            ResultSet result = statement.executeQuery();

            // If this user is a member of a group...
            if(result.next()) return result.getString(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return "§3Speler";
    }

    public static boolean hasPermission(String user, String permission) {
        return (Permissions.getPermissionEntry(user, permission) == null) ? false : true;
    }

    public static String[] getPermissionEntry(String user, String permission) {
        return Permissions.getPermissionEntry(user, permission, "bungeecord", "bungeecord");
    }

    public static String[] getPermissionEntry(String user, String permission, String server, String world) {
        Connection conn = Main.conn;

        try {
            // Create a new select statement
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT `bs_perm_perms`.`permission` AS `permission`,\n" +
                    "       `bs_perm_perms`.`options` AS `options`,\n" +
                    "       `bs_perm_groups`.`name` AS `group_name`\n" +

                    "FROM `bs_perm_perms`\n" +

                    "INNER JOIN `bs_perm_groups` ON `bs_perm_perms`.`group` = `bs_perm_groups`.`id`\n" +
                    "INNER JOIN `bs_perm_members` ON `bs_perm_perms`.`group` = `bs_perm_members`.`group`\n" +

                    "WHERE `bs_perm_members`.`user` IN (?, 'default')\n" +
                    "  AND ? REGEXP `bs_perm_members`.`server`\n" +
                    "  AND ? REGEXP `bs_perm_perms`.`world`\n" +
                    "  AND ? REGEXP `bs_perm_perms`.`permission`\n" +

                    "ORDER BY `bs_perm_groups`.`id` DESC\n" +
                    "LIMIT 1;");

            statement.setString(1, user);
            statement.setString(2, server);
            statement.setString(3, world);
            statement.setString(4, permission);

            ResultSet result = statement.executeQuery();

            if(result.next()) return new String[] { result.getString(1),
                                                    result.getString(2),
                                                    result.getString(3) };
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
