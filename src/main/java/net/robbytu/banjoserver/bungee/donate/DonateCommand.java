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
            else sender.sendMessage(ChatColor.AQUA + "Je kan de volgende donaties doen in de " + Main.instance.getProxy().getPlayer(sender.getName()).getServer().getInfo().getName() + " server");

            for(Donation donation : donations) {
                sender.sendMessage(" ");
                sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + " * " + ChatColor.RESET + ChatColor.BLUE + donation.title);
                sender.sendMessage(ChatColor.GRAY + "   €" + DonateUtil.prices.get(donation.code) + " - /doneer " + donation.tag);
            }

            if(donations.length > 0) sender.sendMessage(" "); // Seperate chat from command, looks a bit cleaner :)
        }

        else if (args.length == 1) {
            Donation[] donations = DonateUtil.getDonationsForServer(Main.instance.getProxy().getPlayer(sender.getName()).getServer().getInfo().getName());
            Donation donation = null;

            for(Donation d : donations) if(d.tag.equalsIgnoreCase(args[0])) donation = d;

            if(donation == null) {
                sender.sendMessage(ChatColor.RED + "Er is geen donatie in deze server mogelijk met de tag " + args[0]);
                return;
            }

            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + donation.title + " - " + Main.instance.getProxy().getPlayer(sender.getName()).getServer().getInfo().getName() + " server");
            sender.sendMessage(ChatColor.GRAY + donation.description);
            sender.sendMessage(" ");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Doneren: " + ChatColor.RESET + ChatColor.BLUE + "/sms " + donation.tag);
        }
    }
}
