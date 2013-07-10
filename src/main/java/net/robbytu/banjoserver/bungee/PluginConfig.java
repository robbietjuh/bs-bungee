package net.robbytu.banjoserver.bungee;

import net.craftminecraft.bungee.bungeeyaml.supereasyconfig.Config;

import java.io.File;

public class PluginConfig extends Config {

    public PluginConfig(Main plugin) {
        CONFIG_FILE = new File("plugins" + File.separator + plugin.getDescription().getName(), "config.yml");
        CONFIG_HEADER = "bs-bungee config file";

        try {
            this.init();
            plugin.getLogger().info("Configuration loaded");
        }
        catch(Exception ex) {
            plugin.getLogger().warning("Could not load configuration!");
            ex.printStackTrace();
        }
    }

    public String db_host = "localhost";
    public int db_port = 3306;
    public String db_username = "minecraft";
    public String db_password = "";
    public String db_database = "minecraft";

    public int warns_banAt = 3;
}