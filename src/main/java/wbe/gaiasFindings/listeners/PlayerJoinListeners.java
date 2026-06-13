package wbe.gaiasFindings.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import wbe.gaiasFindings.GaiasFindings;

public class PlayerJoinListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleRuneDataOnJoin(PlayerJoinEvent event) {
        GaiasFindings.utilities.loadPlayerData(event.getPlayer());
    }
}
