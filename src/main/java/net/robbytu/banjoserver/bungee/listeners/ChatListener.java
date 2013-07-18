package net.robbytu.banjoserver.bungee.listeners;

import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.robbytu.banjoserver.bungee.auth.AuthChatListener;
import net.robbytu.banjoserver.bungee.directsupport.TicketChatHandler;
import net.robbytu.banjoserver.bungee.mute.MuteChatListener;

public class ChatListener implements Listener {
    @EventHandler
    public static void handleChat(ChatEvent event) {
        if(!event.isCancelled()) AuthChatListener.handleChat(event);
        if(!event.isCancelled()) TicketChatHandler.handleChat(event);
        if(!event.isCancelled()) MuteChatListener.handleChat(event);
    }
}
