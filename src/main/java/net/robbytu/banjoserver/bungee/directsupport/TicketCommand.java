package net.robbytu.banjoserver.bungee.directsupport;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;
import net.robbytu.banjoserver.bungee.perms.Permissions;

public class TicketCommand extends Command {
    private final String usage = "/ticket [accept/next/list/close] [id]";

    public TicketCommand() {
        super("ticket");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 1) {
            this.failCommand(sender, "Please specify required arguments.");
            return;
        }

        if(args[0].equalsIgnoreCase("accept")) {
            if(args.length != 2) {
                this.failCommand(sender, "Please specify an ID");
                return;
            }

            if(!Permissions.hasPermission(sender.getName(), "bs.bungee.tickets.help")) {
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

            if(!ticket.status.equalsIgnoreCase("open")) {
                this.failCommand(sender, "This ticket is " + ticket.status + ". It should be open for you to accept it.");
                return;
            }

            this.acceptTicket(sender, ticket);
        }
        else if(args[0].equalsIgnoreCase("next")) {
            if(!Permissions.hasPermission(sender.getName(), "bs.bungee.tickets.help")) {
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
        else if(args[0].equalsIgnoreCase("list")) {
            // No permissions check. It might be fun for users to see what admins are doing - and hey, we're not the NSA :P. We're quite open. We love sharing information! :-)
            Ticket[] tickets = Tickets.getOpenTickets();

            if(tickets.length == 0) {
                this.failCommand(sender, "There are no open tickets at this moment.");
                return;
            }

            sender.sendMessage(" ");
            sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Er zijn " + tickets.length + " open tickets.");
            for(Ticket ticket : tickets) sender.sendMessage(ChatColor.GOLD + "  #" + ticket.id + ": " + ((ticket.question.length() > 40) ? ticket.question.substring(0, 40) : ticket.question));
            if(!Permissions.hasPermission(sender.getName(), "bs.bungee.tickets.help")) sender.sendMessage(ChatColor.GRAY + "Je kan tickets accepteren met /ticket accept [id]");
            sender.sendMessage(" ");
        }
        else if(args[0].equalsIgnoreCase("close") || args[0].equalsIgnoreCase("leave")) {
            if(Tickets.inTicket(sender.getName())) {
                Ticket ticket = Tickets.getCurrentTicketForUser(sender.getName());

                Main.instance.getProxy().getPlayer(ticket.admin).sendMessage(ChatColor.GREEN + "Ticket is gesloten.");
                Main.instance.getProxy().getPlayer(ticket.username).sendMessage(ChatColor.GREEN + "Ticket is gesloten.");

                ticket.status = "closed";
                ticket.date_resolved = (int) (System.currentTimeMillis() / 1000L);

                Tickets.updateTicket(ticket);

                Tickets.usersInTicket.remove(ticket.username);
                Tickets.usersInTicket.remove(ticket.admin);
            }
        }
    }

    private void acceptTicket(CommandSender sender, Ticket ticket) {
        ticket.date_accepted = (int) (System.currentTimeMillis() / 1000L);
        ticket.status = "accepted";
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
