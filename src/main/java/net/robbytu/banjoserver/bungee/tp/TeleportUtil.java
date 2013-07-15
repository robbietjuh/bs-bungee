package net.robbytu.banjoserver.bungee.tp;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.robbytu.banjoserver.bungee.PluginMessager;

import java.util.HashMap;

public class TeleportUtil {
    private static HashMap<ProxiedPlayer, ProxiedPlayer> requests = new HashMap<ProxiedPlayer, ProxiedPlayer>();
    private static HashMap<ProxiedPlayer, ProxiedPlayer> targets = new HashMap<ProxiedPlayer, ProxiedPlayer>();
    public static HashMap<ProxiedPlayer, Integer> timings = new HashMap<ProxiedPlayer, Integer>();

    public static boolean isAwaitingApproval(ProxiedPlayer sender, ProxiedPlayer target) {
        if(requests.containsKey(sender)) return (requests.get(sender).equals(target));
        else return false;
    }

    public static void requestTeleport(ProxiedPlayer sender, ProxiedPlayer target) {
        if(requests.containsKey(sender)) {
            requests.get(sender).sendMessage(ChatColor.RED + sender.getName() + " heeft het verzoek geannuleerd.");
            requests.remove(sender);
        }

        requests.put(sender, target);
        targets.put(target, sender);

        target.sendMessage(" ");
        target.sendMessage(ChatColor.GOLD + sender.getName() + " wil naar je teleporteren.");
        target.sendMessage(ChatColor.GRAY + " * " + ChatColor.DARK_AQUA + "/tpaccept" + ChatColor.GRAY + "   Accepteer het verzoek");
        target.sendMessage(ChatColor.GRAY + " * " + ChatColor.DARK_AQUA + "/tpdeny" + ChatColor.GRAY + "     Weiger het verzoek");
        target.sendMessage(" ");
    }

    public static boolean acceptRequest(ProxiedPlayer target) {
        ProxiedPlayer sender = targets.get(target);
        if(sender == null) return false;

        if(timings.get(target) != null) {
            if(timings.get(target) > (int) (System.currentTimeMillis() / 1000L)) {
                sender.sendMessage(ChatColor.GRAY + "Er is een cooldown van toepassing. Probeer het over een paar seconden nogmaals.");
                return false;
            }
        }

        if(timings.get(sender) != null) {
            if(timings.get(sender) > (int) (System.currentTimeMillis() / 1000L)) {
                sender.sendMessage(ChatColor.GRAY + "Er is een cooldown van toepassing. Probeer het over een paar seconden nogmaals.");
                return false;
            }
        }

        targets.remove(target);
        requests.remove(sender);

        sender.sendMessage(ChatColor.GRAY + "Teleporteren...");
        target.sendMessage(ChatColor.GRAY + "Teleporteren...");

        if(!target.getServer().getInfo().equals(sender.getServer().getInfo())) {
            sender.connect(target.getServer().getInfo());
        }

        PluginMessager.sendMessage(target.getServer(), "teleport", sender.getName(), target.getName());

        timings.put(target, (int) (System.currentTimeMillis() / 1000L) + 3);
        timings.put(sender, (int) (System.currentTimeMillis() / 1000L) + 3);

        return true;
    }

    public static boolean denyRequest(ProxiedPlayer target) {
        ProxiedPlayer sender = targets.get(target);
        if(sender == null) return false;

        targets.remove(target);
        requests.remove(sender);

        sender.sendMessage(ChatColor.RED + "Verzoek om te teleporteren is geweigerd.");
        return true;
    }
}
