package net.robbytu.banjoserver.bungee.pm;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;

public class PmSession {
    public static HashMap<ProxiedPlayer, ProxiedPlayer> replyTo = new HashMap<ProxiedPlayer, ProxiedPlayer>();
}
