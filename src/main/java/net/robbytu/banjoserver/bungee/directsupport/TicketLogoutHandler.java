package net.robbytu.banjoserver.bungee.directsupport;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.robbytu.banjoserver.bungee.Main;

public class TicketLogoutHandler implements Listener {
    @EventHandler
    public static void logoutHandler(PlayerDisconnectEvent event) {
        if(Tickets.inTicket(event.getPlayer().getName())) {
            Ticket ticket = Tickets.getCurrentTicketForUser(event.getPlayer().getName());
            ticket.status = "closed_by_logout";
            ticket.date_resolved = (int) (System.currentTimeMillis() / 1000L);

            if(!ticket.admin.equals("") && !ticket.admin.equals(event.getPlayer().getName())) {
                Main.instance.getProxy().getPlayer(ticket.admin).sendMessage(ChatColor.RED + " * Ticket gesloten: " + event.getPlayer().getName() + " is uitgelogd.");
                Tickets.usersInTicket.remove(ticket.admin);
            }

            if(ticket.admin.equals(event.getPlayer().getName()) && Main.instance.getProxy().getPlayer(ticket.username) != null) {
                Main.instance.getProxy().getPlayer(ticket.username).sendMessage(ChatColor.RED + " * Ticket gesloten: " + event.getPlayer().getName() + " is uitgelogd.");
                Tickets.usersInTicket.remove(ticket.username);
            }

            Tickets.usersInTicket.remove(event.getPlayer().getName());

            Tickets.updateTicket(ticket);
        }
    }
}
