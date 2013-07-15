package net.robbytu.banjoserver.bungee.directsupport;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;

public class HelpCommand extends Command {
    private final String usage = "/help [vraag]";

    public HelpCommand() {
        super("help", null, "helpop", "ds");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(Tickets.getTickets(sender.getName(), "open").length != 0) {
            sender.sendMessage(ChatColor.RED + "Je hebt al een open ticket.");
            return;
        }

        if(Tickets.inTicket(sender.getName())) {
            sender.sendMessage(ChatColor.RED + "Je wordt al geholpen.");
            return;
        }

        boolean adminsOnline = false;
        for(ProxiedPlayer player : Main.instance.getProxy().getPlayers()) if(player.hasPermission("bs.admin")) adminsOnline = true;

        if(!adminsOnline) {
            sender.sendMessage(ChatColor.RED + "Er zijn op dit moment geen admins online om je te helpen. Probeer het later nog eens.");
            return;
        }

        String question = "";
        for(int i = 0; i < args.length; i++) {
            if(!args[i].equals("create")) question += ((question.equals("")) ? "" : " ") + args[i];
        }

        Ticket ticket = new Ticket();

        ticket.username = sender.getName();
        ticket.server = Main.instance.getProxy().getPlayer(sender.getName()).getServer().getInfo().getName();
        ticket.question = question;

        Tickets.createTicket(ticket);
        sender.sendMessage(ChatColor.GREEN + "Je ticket is aangemaakt. Je wordt zo snel mogelijk geholpen!");
    }
}