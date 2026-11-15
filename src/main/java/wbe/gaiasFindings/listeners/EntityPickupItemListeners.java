package wbe.gaiasFindings.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.gaiasFindings.GaiasFindings;
import wbe.gaiasFindings.config.Rune;
import wbe.gaiasFindings.config.SackRune;
import wbe.gaiasFindings.items.RuneSack;

public class EntityPickupItemListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void addGemsToSack(EntityPickupItemEvent event) {
        if(event.isCancelled()) {
            return;
        }

        if(!(event.getEntity() instanceof Player player)) {
            return;
        }

        if(!player.getInventory().containsAtLeast(new RuneSack(), 1)) {
            return;
        }

        ItemStack item = event.getItem().getItemStack();
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return;
        }

        NamespacedKey typeKey = new NamespacedKey(GaiasFindings.getInstance(), "runeType");
        if(!meta.getPersistentDataContainer().has(typeKey)) {
            return;
        }

        Rune rune = GaiasFindings.config.runes.get(meta.getPersistentDataContainer().get(typeKey, PersistentDataType.STRING));
        SackRune sackRune = GaiasFindings.utilities.getSackRune(player, rune.getId());
        if(sackRune == null) {
            sackRune = new SackRune(rune, item.getAmount());
            GaiasFindings.playerSacks.get(player).add(sackRune);
        } else {
            sackRune.setAmount(sackRune.getAmount() + item.getAmount());
        }

        player.playSound(player, GaiasFindings.config.addRuneToSackSound, 1f, 1f);

        event.getItem().remove();
        event.setCancelled(true);
    }
}
