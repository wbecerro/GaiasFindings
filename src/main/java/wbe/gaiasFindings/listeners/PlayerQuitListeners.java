package wbe.gaiasFindings.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import wbe.gaiasFindings.GaiasFindings;

public class PlayerQuitListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleRuneDataOnQuit(PlayerQuitEvent event) {
        GaiasFindings.utilities.savePlayerData(event.getPlayer());
    }
}
