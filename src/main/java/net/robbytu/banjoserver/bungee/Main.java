package net.robbytu.banjoserver.bungee;

import net.craftminecraft.bungee.bungeeyaml.bukkitapi.InvalidConfigurationException;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.robbytu.banjoserver.bungee.automessages.BroadcastTask;
import net.robbytu.banjoserver.bungee.bans.*;
import net.robbytu.banjoserver.bungee.directsupport.HelpCommand;
import net.robbytu.banjoserver.bungee.directsupport.ReminderTask;
import net.robbytu.banjoserver.bungee.directsupport.TicketChatHandler;
import net.robbytu.banjoserver.bungee.kicks.KickCommand;
import net.robbytu.banjoserver.bungee.list.ListCommand;
import net.robbytu.banjoserver.bungee.listeners.LoginListener;
import net.robbytu.banjoserver.bungee.listeners.PluginMessageListener;
import net.robbytu.banjoserver.bungee.mail.MailCheckTask;
import net.robbytu.banjoserver.bungee.mail.MailLoginHandler;
import net.robbytu.banjoserver.bungee.mute.MuteChatListener;
import net.robbytu.banjoserver.bungee.tp.TpAcceptCommand;
import net.robbytu.banjoserver.bungee.tp.TpCommand;
import net.robbytu.banjoserver.bungee.tp.TpDenyCommand;
import net.robbytu.banjoserver.bungee.warns.WarnsCommand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.TimeUnit;

public class Main extends Plugin {
    public static final String PLUGIN_NAME = "bs-bungee";
    public static Main instance;
    public static PluginConfig config;
    public static Connection conn;

    public void onLoad() {
        instance = this;
        config = new PluginConfig(this);
    }

    public void onEnable() {
        // Set up a connection
        try{
            conn = DriverManager.getConnection("jdbc:mysql://" + config.db_host + ":" + config.db_port + "/" + config.db_database + "?autoReconnect=true", config.db_username, config.db_password);
        }
        catch (Exception e) {
            getLogger().warning("bs-bungee has not been enabled. Please check your configuration.");
            e.printStackTrace();
        }

        getLogger().info("Registering commands...");
        this.registerCommands();

        getLogger().info("Registering listeners...");
        this.registerListeners();

        getLogger().info("Registering scheduled tasks...");
        this.registerSchedulers();

        getLogger().info("Registering for plugin channels...");
        this.registerChannels();
    }

    private void registerCommands() {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new WarnsCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new BanCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TempBanCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new KickCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new IPBanCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new UnbanIPCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new UnbanCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TpCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TpAcceptCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TpDenyCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new HelpCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ListCommand());
    }

    private void registerListeners() {
        ProxyServer.getInstance().getPluginManager().registerListener(this, new LoginListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PluginMessageListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new TicketChatHandler());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new MailLoginHandler());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new MuteChatListener());
    }

    private void registerSchedulers() {
        ProxyServer.getInstance().getScheduler().schedule(this, new BroadcastTask(), 300, TimeUnit.SECONDS);
        ProxyServer.getInstance().getScheduler().schedule(this, new ReminderTask(), 60, TimeUnit.SECONDS);
        ProxyServer.getInstance().getScheduler().schedule(this, new MailCheckTask(), 300, TimeUnit.SECONDS);
    }

    private void registerChannels() {
        ProxyServer.getInstance().registerChannel("BSBungee");
    }

    public void onDisable() {
        try {
            config.save();
            getLogger().info("Saved configuration.");
        } catch (InvalidConfigurationException e) {
            getLogger().warning("Could not save configuration file to disk.");
            e.printStackTrace();
        }
    }
}