package net.robbytu.banjoserver.bungee.votes;

/*
 * Copyright (C) 2012 Vex Software LLC
 * This file is part of Votifier. Modified for use in bs-bungee by RobbytuProjects.
 *
 * Votifier is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Votifier is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Votifier.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.KeyPair;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import javax.crypto.BadPaddingException;

import com.vexsoftware.votifier.crypto.RSA;
import com.vexsoftware.votifier.crypto.RSAIO;
import com.vexsoftware.votifier.crypto.RSAKeygen;
import net.robbytu.banjoserver.bungee.Main;

/**
 * The vote receiving server.
 *
 * @author Blake Beaupain
 * @author Kramer Campbell
 * @author Robert de Vries <r.devries@robbytu.net>
 */
public class VoteReceiver extends Thread {
    private final String host;
    private final int port;
    private ServerSocket server;
    private boolean running = true;
    private KeyPair keyPair;

    public VoteReceiver(String host, int port) {
        this.host = host;
        this.port = port;

        initialize();
    }

    private void initialize() {
        try {
            File rsaDirectory = new File("plugins" + File.separator + Main.instance.getDescription().getName() + File.separator + "rsa");
            if (!rsaDirectory.exists()) {
                if(!new File("plugins" + File.separator + Main.instance.getDescription().getName()).exists()) new File("plugins" + File.separator + Main.instance.getDescription().getName()).mkdir();
                rsaDirectory.mkdir();
                keyPair = RSAKeygen.generate(2048);
                RSAIO.save(rsaDirectory, keyPair);
            } else {
                keyPair = RSAIO.load(rsaDirectory);
            }

            server = new ServerSocket();
            server.bind(new InetSocketAddress(host, port));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void shutdown() {
        running = false;
        if (server == null) return;

        try { server.close(); }
        catch (Exception ignored) {}
    }

    @Override
    public void run() {
        while (running) {
            try {
                Socket socket = server.accept();
                socket.setSoTimeout(5000); // Don't hang on slow connections.
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                InputStream in = socket.getInputStream();

                // Send them our version.
                writer.write("VOTIFIER bs-bungee");
                writer.newLine();
                writer.flush();

                // Read the 256 byte block.
                byte[] block = new byte[256];
                in.read(block, 0, block.length);

                // Decrypt the block.
                block = RSA.decrypt(block, keyPair.getPrivate());
                int position = 0;

                // Perform the opcode check.
                String opcode = readString(block, position);
                position += opcode.length() + 1;
                if (!opcode.equals("VOTE")) {
                    // Something went wrong in RSA.
                    throw new Exception("Unable to decode RSA");
                }

                // Parse the block.
                String serviceName = readString(block, position);
                position += serviceName.length() + 1;
                String username = readString(block, position);
                position += username.length() + 1;
                String address = readString(block, position);
                position += address.length() + 1;
                String timeStamp = readString(block, position);
                position += timeStamp.length() + 1;

                // Create the vote.
                final Vote vote = new Vote();
                vote.serviceName = serviceName;
                vote.username = username;
                vote.address = address;
                vote.timestamp = ((int) (System.currentTimeMillis() / 1000L)) + 60*60;

                // Call event in a synchronized fashion to ensure that the
                // custom event runs in the
                // the main server thread, not this one.
                Main.instance.getProxy().getScheduler().schedule(Main.instance, new Runnable() {
                    public void run() {
                        Votes.handleVote(vote);
                    }
                }, 1, TimeUnit.SECONDS);

                // Clean up.
                writer.close();
                in.close();
                socket.close();
            } catch (Exception ignored) {}
        }
    }

    private String readString(byte[] data, int offset) {
        StringBuilder builder = new StringBuilder();
        for (int i = offset; i < data.length; i++) {
            if (data[i] == '\n')
                break; // Delimiter reached.
            builder.append((char) data[i]);
        }
        return builder.toString();
    }
}