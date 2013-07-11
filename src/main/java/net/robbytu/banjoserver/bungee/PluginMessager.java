package net.robbytu.banjoserver.bungee;

import net.md_5.bungee.api.connection.Server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PluginMessager {
    public static void sendMessage(Server server, String... message) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream o = new DataOutputStream(b);

        try {
            for(String m : message) {
                o.writeUTF(m);
            }
        }
        catch(IOException ignored) {}

        server.sendData("BSBungee", b.toByteArray());
    }
}
