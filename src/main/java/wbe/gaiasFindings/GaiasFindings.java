package wbe.gaiasFindings;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import wbe.gaiasFindings.commands.CommandListener;
import wbe.gaiasFindings.commands.TabListener;
import wbe.gaiasFindings.config.Config;
import wbe.gaiasFindings.config.Messages;
import wbe.gaiasFindings.config.SackRune;
import wbe.gaiasFindings.listeners.EventListeners;
import wbe.gaiasFindings.papi.PapiExtension;
import wbe.gaiasFindings.utils.Scheduler;
import wbe.gaiasFindings.utils.Utilities;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public final class GaiasFindings extends JavaPlugin {

    private FileConfiguration configuration;

    private CommandListener commandListener;

    private TabListener tabListener;

    private EventListeners eventListeners;

    private PapiExtension papiExtension;

    public static Config config;

    public static Messages messages;

    public static Utilities utilities;

    public static HashMap<Player, List<SackRune>> playerSacks = new HashMap<>();

    @Override
    public void onEnable() {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            papiExtension = new PapiExtension();
            papiExtension.register();
        }
        saveDefaultConfig();
        getLogger().info("Gaia's Findings enabled correctly.");
        reloadConfiguration();

        commandListener = new CommandListener();
        getCommand("gaiasfindings").setExecutor(commandListener);
        tabListener = new TabListener();
        getCommand("gaiasfindings").setTabCompleter(tabListener);
        eventListeners = new EventListeners();
        eventListeners.initializeListeners();
        Scheduler.startSchedulers();
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        reloadConfig();
        for(Player player : playerSacks.keySet()) {
            utilities.savePlayerData(player);
        }

        getLogger().info("Gaia's Findings disabled correctly.");
    }

    public static GaiasFindings getInstance() {
        return getPlugin(GaiasFindings.class);
    }

    public void reloadConfiguration() {
        if(!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }

        new File(getDataFolder(), "saves").mkdir();
        for(Player player : playerSacks.keySet()) {
            utilities.savePlayerData(player);
        }

        reloadConfig();
        configuration = getConfig();
        config = new Config(configuration);
        messages = new Messages(configuration);
        utilities = new Utilities();
    }
}
