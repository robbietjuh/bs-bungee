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
            // BS-52
        }
        else if(args[0] == "next") {
            // BS-54
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

    private void failCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
        sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
    }
}
