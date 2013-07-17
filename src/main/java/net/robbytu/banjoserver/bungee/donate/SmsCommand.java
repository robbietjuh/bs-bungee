package net.robbytu.banjoserver.bungee.donate;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.Main;

public class SmsCommand extends Command {
    public SmsCommand() {
        super("sms");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Kies een tag om te doneren.");
            return;
        }

        if(args.length == 1) {
            Donation[] donations = DonateUtil.getDonationsForServer(Main.instance.getProxy().getPlayer(sender.getName()).getServer().getInfo().getName());
            Donation donation = null;

            for(Donation d : donations) if(d.tag.equalsIgnoreCase(args[0])) donation = d;

            if(donation == null) {
                sender.sendMessage(ChatColor.RED + "Er is geen donatie in deze server mogelijk met de tag " + args[0]);
                return;
            }

            sender.sendMessage("");
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + donation.title + " - " + Main.instance.getProxy().getPlayer(sender.getName()).getServer().getInfo().getName() + " server");
            sender.sendMessage(ChatColor.GRAY + "Sms " + ChatColor.BOLD + ChatColor.WHITE + "BETAAL " + donation.code + ChatColor.RESET + ChatColor.GRAY + " naar " + ChatColor.BOLD + ChatColor.WHITE + "3030" + ChatColor.RESET + ChatColor.GRAY + ". Dit kost eenmalig €" + DonateUtil.prices.get(donation.code) + ". Voer de ontvangen code vervolgens in met " + ChatColor.RESET + ChatColor.WHITE + "/sms " + donation.tag + " [code]" + ChatColor.RESET + ChatColor.GRAY + " om je donatie te voltooien.");
            sender.sendMessage("");
        }
    }
}
