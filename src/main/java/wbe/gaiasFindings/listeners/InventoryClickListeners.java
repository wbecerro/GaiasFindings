package wbe.gaiasFindings.listeners;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.gaiasFindings.GaiasFindings;
import wbe.gaiasFindings.config.Rune;

public class InventoryClickListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void applyGem(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(!event.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)) {
            return;
        }

        if(!(event.getClick().equals(ClickType.LEFT))) {
            return;
        }

        ItemStack runeItem = event.getCursor();
        ItemMeta meta = runeItem.getItemMeta();
        if(meta == null) {
            return;
        }

        NamespacedKey typeKey = new NamespacedKey(GaiasFindings.getInstance(), "runeType");
        if(!meta.getPersistentDataContainer().has(typeKey)) {
            return;
        }

        Rune rune = GaiasFindings.config.runes.get(meta.getPersistentDataContainer().get(typeKey, PersistentDataType.STRING));

        ItemStack inventoryItem = event.getCurrentItem();
        ItemMeta inventoryItemMeta = inventoryItem.getItemMeta();
        if(inventoryItemMeta == null) {
            inventoryItemMeta = Bukkit.getItemFactory().getItemMeta(inventoryItem.getType());
        }

        if(inventoryItem.getAmount() != 1) {
            return;
        }

        String name = "";
        if(!inventoryItemMeta.hasDisplayName()) {
            name = inventoryItemMeta.getItemName();
        } else {
            name = inventoryItemMeta.getDisplayName();
        }

        inventoryItemMeta.setDisplayName(name);
        inventoryItem.setItemMeta(inventoryItemMeta);

        ItemStack newItem = new ItemStack(inventoryItem.getType());
        newItem.setItemMeta(inventoryItemMeta);
        newItem.getItemMeta().setLore(inventoryItemMeta.getLore());

        boolean correct = GaiasFindings.utilities.applyRune(rune, newItem, player);
        if(!correct) {
            event.setCancelled(true);
            return;
        }

        runeItem.setAmount(runeItem.getAmount() - 1);
        event.setCurrentItem(newItem);
        player.setItemOnCursor(runeItem);
        event.setCancelled(true);
        player.updateInventory();
    }
}
