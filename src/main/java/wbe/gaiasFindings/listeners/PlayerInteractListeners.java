package wbe.gaiasFindings.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import wbe.gaiasFindings.GaiasFindings;

public class PlayerInteractListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void openRuneSackOnInteract(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack item = event.getItem();
            NamespacedKey sackKey = new NamespacedKey(GaiasFindings.getInstance(), "runesack");
            if(!GaiasFindings.utilities.checkItem(item, sackKey)) {
                return;
            }

            event.setCancelled(true);
            try {
                MenuListener.openMenu(event.getPlayer(), 1);
            } catch(Exception e) {
                event.getPlayer().sendMessage(e.getMessage());
            }
        }
    }
}
