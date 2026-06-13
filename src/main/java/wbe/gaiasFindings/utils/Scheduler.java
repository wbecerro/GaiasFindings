package wbe.gaiasFindings.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import wbe.gaiasFindings.GaiasFindings;

public class Scheduler {

    private static GaiasFindings plugin;

    public static void startSchedulers() {
        plugin = GaiasFindings.getInstance();
        startDataSaveScheduler();
    }

    private static void startDataSaveScheduler() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for(Player player : GaiasFindings.playerSacks.keySet()) {
                    GaiasFindings.utilities.savePlayerData(player);
                }
            }
        }, 10L, 60 * 5 * 20L);
    }
}
