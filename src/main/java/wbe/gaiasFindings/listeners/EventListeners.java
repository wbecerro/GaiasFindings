package wbe.gaiasFindings.listeners;

import org.bukkit.plugin.PluginManager;
import wbe.gaiasFindings.GaiasFindings;

public class EventListeners {

    public void initializeListeners() {
        GaiasFindings plugin = GaiasFindings.getInstance();
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new BlockBreakListeners(), plugin);
        pluginManager.registerEvents(new InventoryClickListeners(), plugin);
        pluginManager.registerEvents(new PlayerDropItemListeners(), plugin);
        pluginManager.registerEvents(new PlayerReceiveRuneListeners(), plugin);
    }
}