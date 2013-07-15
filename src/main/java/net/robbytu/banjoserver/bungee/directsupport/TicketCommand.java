package net.robbytu.banjoserver.bungee.directsupport;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;

public class TicketCommand extends Command {
    private final String usage = "/ticket [accept/next/close] [id]";

    public TicketCommand() {
        super("ticket");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 1) {
            this.failCommand(sender, "Please specify required arguments.");
            return;
        }

        if(args[0] == "accept") {
            if(args.length != 2) {
                this.failCommand(sender, "Please specify an ID");
                return;
            }

            if(!sender.hasPermission("bs.admin") && !sender.hasPermission("bs.helper")) {
                this.failCommand(sender, "You're not allowed to accept tickets.");
                return;
            }

            if(Tickets.inTicket(sender.getName())) {
                this.failCommand(sender, "You're already helping someone else.");
                return;
            }

            Ticket ticket = Tickets.getTicket(args[1]);
            if(ticket == null) {
                this.failCommand(sender, "Unknown ticket.");
                return;
            }

            if(ticket.status != "open") {
                this.failCommand(sender, "This ticket is " + ticket.status + ". It should be open for you to accept it.");
                return;
            }

            this.acceptTicket(sender, ticket);
        }
        else if(args[0] == "next") {
            if(!sender.hasPermission("bs.admin") && !sender.hasPermission("bs.helper")) {
                this.failCommand(sender, "You're not allowed to accept tickets.");
                return;
            }

            if(Tickets.inTicket(sender.getName())) {
                this.failCommand(sender, "You're already helping someone else.");
                return;
            }

            if(Tickets.getOpenTickets().length == 0) {
                this.failCommand(sender, "There are no open tickets at this moment.");
                return;
            }

            this.acceptTicket(sender, Tickets.getOpenTickets()[0]);
        }
        else if(args[0] == "close") {
            if(Tickets.inTicket(sender.getName())) {
                Ticket ticket = Tickets.getCurrentTicketForUser(sender.getName());

                Main.instance.getProxy().getPlayer(ticket.admin).sendMessage(ChatColor.GREEN + "Ticket is gesloten.");
                Main.instance.getProxy().getPlayer(ticket.username).sendMessage(ChatColor.GREEN + "Ticket is gesloten.");

                ticket.status = "closed";
                ticket.date_resolved = (int) (System.currentTimeMillis() / 1000L);

                Tickets.updateTicket(ticket);
            }
        }
    }

    private void acceptTicket(CommandSender sender, Ticket ticket) {
        ticket.date_accepted = (int) (System.currentTimeMillis() / 1000L);
        ticket.admin = sender.getName();
        Tickets.updateTicket(ticket);

        Tickets.usersInTicket.put(sender.getName(), "" + ticket.id); // Lazy hack, I know... But it works :-/
        Tickets.usersInTicket.put(ticket.username, "" + ticket.id);

        Main.instance.getProxy().getPlayer(ticket.username).sendMessage(ChatColor.GREEN + "Je wordt nu geholpen door " + sender.getName());

        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.AQUA + "Ticket #" + ticket.id + " door " + ticket.username + " in " + ticket.server);
        sender.sendMessage(ChatColor.AQUA + " * " + ticket.question);
        sender.sendMessage(" ");
    }

    private void failCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
        sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
    }
}
