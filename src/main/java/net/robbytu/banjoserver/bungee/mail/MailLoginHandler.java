package net.robbytu.banjoserver.bungee.mail;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;

public class MailLoginHandler implements Listener {
    public static void handleLoginEvent(PostLoginEvent event) {
        Mail[] mails = Mails.getMailForUser(event.getPlayer().getName(), 0);
        if(mails.length > 0) {
            boolean unreadMessages = false;
            for(Mail mail : mails) if(mail.unread) unreadMessages = true;

            if(unreadMessages) event.getPlayer().sendMessage(ChatColor.GRAY + "Je hebt nieuwe mails! Je kan ze lezen met /mail read");
        }
    }
}
