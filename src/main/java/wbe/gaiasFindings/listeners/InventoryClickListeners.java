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
import wbe.gaiasFindings.config.SackRune;

public class InventoryClickListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void applyRune(InventoryClickEvent event) {
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

    @EventHandler(priority = EventPriority.NORMAL)
    public void addRunesToSack(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(!event.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)) {
            return;
        }

        if(!event.getClick().equals(ClickType.RIGHT)) {
            return;
        }

        ItemStack runeItem = event.getCursor();
        ItemStack sackItem = event.getCurrentItem();
        ItemMeta meta = runeItem.getItemMeta();
        if(meta == null) {
            return;
        }

        NamespacedKey typeKey = new NamespacedKey(GaiasFindings.getInstance(), "runeType");
        NamespacedKey sackKey = new NamespacedKey(GaiasFindings.getInstance(), "runesack");
        // Cursor es runa
        if(!meta.getPersistentDataContainer().has(typeKey)) {
            return;
        }

        // Current item es saco
        if(!GaiasFindings.utilities.checkItem(sackItem, sackKey)) {
            return;
        }

        Rune rune = GaiasFindings.config.runes.get(meta.getPersistentDataContainer().get(typeKey, PersistentDataType.STRING));
        SackRune sackRune = GaiasFindings.utilities.getSackRune(player, rune.getId());
        if(sackRune == null) {
            sackRune = new SackRune(rune, runeItem.getAmount());
            GaiasFindings.playerSacks.get(player).add(sackRune);
        } else {
            sackRune.setAmount(sackRune.getAmount() + runeItem.getAmount());
        }

        player.playSound(player, GaiasFindings.config.addRuneToSackSound, 1f, 1f);

        runeItem.setAmount(0);
        player.setItemOnCursor(runeItem);
        event.setCancelled(true);
        player.updateInventory();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void cancelGemSackBundleFunctions(InventoryClickEvent event) {
        if(event.getAction().equals(InventoryAction.valueOf("PICKUP_ALL_INTO_BUNDLE")) ||
                event.getAction().equals(InventoryAction.valueOf("PICKUP_SOME_INTO_BUNDLE")) ||
                event.getAction().equals(InventoryAction.valueOf("PICKUP_FROM_BUNDLE")) ||
                event.getAction().equals(InventoryAction.valueOf("PLACE_ALL_INTO_BUNDLE")) ||
                event.getAction().equals(InventoryAction.valueOf("PLACE_FROM_BUNDLE"))||
                event.getAction().equals(InventoryAction.valueOf("PLACE_SOME_INTO_BUNDLE"))) {
            ItemStack currentItem = event.getCurrentItem();
            ItemStack cursor = event.getCursor();
            NamespacedKey sackKey = new NamespacedKey(GaiasFindings.getInstance(), "runesack");
            if(!GaiasFindings.utilities.checkItem(currentItem, sackKey)) {
                if(!GaiasFindings.utilities.checkItem(cursor, sackKey)) {
                    return;
                }
            }

            event.setCancelled(true);
        } else if((event.getAction().toString().contains("PICKUP") || event.getAction().equals(InventoryAction.NOTHING)) && event.getClick().equals(ClickType.RIGHT)) {
            ItemStack currentItem = event.getCurrentItem();
            NamespacedKey sackKey = new NamespacedKey(GaiasFindings.getInstance(), "runesack");
            if(!GaiasFindings.utilities.checkItem(currentItem, sackKey)) {
                return;
            }

            event.setCancelled(true);
            try {
                MenuListener.openMenu((Player) event.getWhoClicked(), 1);
            } catch(Exception e) {
                event.getWhoClicked().sendMessage(e.getMessage());
            }
        }
    }
}
