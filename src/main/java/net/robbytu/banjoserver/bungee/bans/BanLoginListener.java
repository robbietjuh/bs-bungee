package net.robbytu.banjoserver.bungee.bans;

import com.google.common.eventbus.Subscribe;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;

public class BanLoginListener implements Listener {
    @Subscribe
    public void login(LoginEvent event) {
        // User bans
        Ban[] bans = Bans.getUserBans(event.getConnection().getName(), true);
        for(Ban ban : bans) {
            if(!ban.isTempban()) {
                event.setCancelReason(ChatColor.RED + "" + ChatColor.BOLD + ban.reason + "\n\n" + ChatColor.RESET + "Je kan een Ban Appeal maken op onze website: www.banjoserver.nl");
                event.setCancelled(true);
                return;
            }
            if(ban.isTempban()) {
                if(ban.tempban > (int) (System.currentTimeMillis() / 1000L)) {
                    event.setCancelReason("Je bent tijdelijk gebanned voor " + ban.tempban + " sec:\n\n" + ChatColor.RED + ban.reason);
                    event.setCancelled(true);
                    return;
                }
                else {
                    ban.active = false;
                    Bans.updateUserBan(ban);
                }
            }
        }

        // IP bans
        Ban[] ipbans = Bans.getUserBans(event.getConnection().getAddress().getAddress().toString(), true);
        for(Ban ban : ipbans) {
            if(!ban.isTempban()) {
                event.setCancelReason(ChatColor.RED + "IP ban: " + ChatColor.BOLD + ban.reason + "\n\n" + ChatColor.RESET + "Je kan een Ban Appeal maken op onze website: www.banjoserver.nl");
                event.setCancelled(true);
                return;
            }
        }
    }
}
