package net.robbytu.banjoserver.bungee.mail;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.robbytu.banjoserver.bungee.Main;

import java.util.concurrent.TimeUnit;

public class MailCheckTask implements Runnable {
    public void run() {
        for(ProxiedPlayer player : Main.instance.getProxy().getPlayers()) {
            Mail[] mails = Mails.getMailForUser(player.getName(), 0);

            if(mails.length > 0) {
                boolean unreadMessages = false;
                for(Mail mail : mails) if(mail.unread) unreadMessages = true;

                if(unreadMessages) player.sendMessage(ChatColor.GRAY + "Je hebt nieuwe mails! Je kan ze lezen met /mail read");
            }
        }

        Main.instance.getProxy().getScheduler().schedule(Main.instance, new MailCheckTask(), 300, TimeUnit.SECONDS);
    }
}
