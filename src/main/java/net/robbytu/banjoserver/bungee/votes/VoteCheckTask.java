package net.robbytu.banjoserver.bungee.votes;

import net.md_5.bungee.api.ChatColor;
import net.robbytu.banjoserver.bungee.Main;

import java.util.concurrent.TimeUnit;

public class VoteCheckTask implements Runnable {
    public void run() {
        for(Vote vote : Votes.openVotes()) {
            if(Main.instance.getProxy().getPlayer(vote.username) != null) {
                Main.instance.getProxy().getPlayer(vote.username).sendMessage(" ");
                Main.instance.getProxy().getPlayer(vote.username).sendMessage(ChatColor.GREEN + "Bedankt voor het stemmen op de Banjoserver.");
                Main.instance.getProxy().getPlayer(vote.username).sendMessage(ChatColor.GRAY + "Type nu " + ChatColor.WHITE + "/vote" + ChatColor.GRAY + " om je beloning te innen!");
                Main.instance.getProxy().getPlayer(vote.username).sendMessage(" ");
            }
        }
        Main.instance.getProxy().getScheduler().schedule(Main.instance, new VoteCheckTask(), 300, TimeUnit.SECONDS);
    }
}