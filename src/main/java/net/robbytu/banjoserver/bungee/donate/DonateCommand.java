package net.robbytu.banjoserver.bungee.donate;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;

public class DonateCommand extends Command {
    public DonateCommand() {
        super("donate", null, "doneer");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            Donation[] donations = DonateUtil.getDonationsForServer(Main.instance.getProxy().getPlayer(sender.getName()).getServer().getInfo().getName());
            if(donations.length == 0) sender.sendMessage(ChatColor.RED + "Je kan geen donaties doen in deze server: " + Main.instance.getProxy().getPlayer(sender.getName()).getServer().getInfo().getName());

            sender.sendMessage(ChatColor.AQUA + "Je kan de volgende donaties doen in de " + Main.instance.getProxy().getPlayer(sender.getName()).getServer().getInfo().getName() + " server");

            for(Donation donation : donations) {
                sender.sendMessage(" ");
                sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + " * " + ChatColor.RESET + ChatColor.BLUE + donation.title);
                sender.sendMessage(ChatColor.GRAY + "   €" + DonateUtil.prices.get(donation.tag) + " - /doneer " + donation.tag);
            }

            if(donations.length > 0) sender.sendMessage(" "); // Seperate chat from command, looks a bit cleaner :)
        }
    }
}
