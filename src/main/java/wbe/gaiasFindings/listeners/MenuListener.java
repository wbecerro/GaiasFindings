package wbe.gaiasFindings.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.gaiasFindings.GaiasFindings;
import wbe.gaiasFindings.config.Rune;
import wbe.gaiasFindings.config.SackRune;
import wbe.gaiasFindings.items.RuneItem;

import java.util.List;

public class MenuListener implements Listener {

    private static void fillBorders(Inventory inventory, int page) {
        ItemStack borde = new ItemStack(GaiasFindings.config.borderMaterial);
        ItemMeta bordeMeta = borde.getItemMeta();
        NamespacedKey currentPage = new NamespacedKey(GaiasFindings.getInstance(), "currentPage");
        bordeMeta.setDisplayName(" ");
        bordeMeta.getPersistentDataContainer().set(currentPage, PersistentDataType.INTEGER, page);
        borde.setItemMeta(bordeMeta);

        for(int i = 0; i < inventory.getSize(); i++) {
            // Primera fila
            if(i<9) {
                inventory.setItem(i, borde);
            }
            // Columna izquierda
            if(i % 9 == 0) {
                inventory.setItem(i, borde);
            }
            // Columna derecha
            if(i % 9 == 8) {
                inventory.setItem(i, borde);
            }
            // Última fila
            if(i >= 45){
                inventory.setItem(i, borde);
            }
        }
    }

    public static void fillRunes(Inventory inventory, int page, List<SackRune> runes) {
        int maxRunesToShow = 28*page;
        int runeIndex = 28*(page-1);
        if(28*page > runes.size()) {
            maxRunesToShow = runes.size();
        }

        for(int i = 0; i < inventory.getSize(); i++) {
            // Primera fila
            if(i<9) {
                continue;
            }
            // Columna izquierda
            if(i % 9 == 0) {
                continue;
            }
            // Columna derecha
            if(i % 9 == 8) {
                continue;
            }
            // Última fila
            if(i >= 45){
                continue;
            }

            if(runeIndex < maxRunesToShow) {
                SackRune sackRune = runes.get(runeIndex);
                ItemStack runeItem = sackRune.getMenuItem();
                inventory.setItem(i, runeItem);
                runeIndex++;
            }
        }
    }

    public static void openMenu(Player player, int page) throws Exception {
        List<SackRune> runes = GaiasFindings.playerSacks.get(player);
        if(runes.size() == 0) {
            throw new Exception(GaiasFindings.messages.noRunesFound);
        }

        int necesaryPages = (int) Math.ceil((double) runes.size() / 28);
        if(page > necesaryPages) {
            throw new Exception(GaiasFindings.messages.pageNotFound);
        }
        NamespacedKey goToPage = new NamespacedKey(GaiasFindings.getInstance(), "goToPage");

        Inventory inventory = Bukkit.createInventory(null, 54, GaiasFindings.config.menuTitle);
        fillBorders(inventory, page);
        fillRunes(inventory, page, runes);

        if(necesaryPages > page) {
            ItemStack nextPage = new ItemStack(Material.ARROW);
            ItemMeta nextPageMeta = nextPage.getItemMeta();
            nextPageMeta.setDisplayName(
                GaiasFindings.messages.menuNextPage.replace("%next_page%", String.valueOf(page+1))
            );
            nextPageMeta.getPersistentDataContainer().set(goToPage, PersistentDataType.INTEGER, page+1);
            nextPage.setItemMeta(nextPageMeta);
            inventory.setItem(53, nextPage);
        }

        if(page > 1) {
            ItemStack backPage = new ItemStack(Material.ARROW);
            ItemMeta backPageMeta = backPage.getItemMeta();
            backPageMeta.setDisplayName(
                GaiasFindings.messages.menuPreviousPage.replace("%previous_page%", String.valueOf(page-1))
            );
            backPageMeta.getPersistentDataContainer().set(goToPage, PersistentDataType.INTEGER, page-1);

            backPage.setItemMeta(backPageMeta);
            inventory.setItem(45, backPage);
        }

        player.openInventory(inventory);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack bordeItem = event.getInventory().getItem(0);
        Inventory inventory = event.getInventory();
        if(bordeItem == null) {
            return;
        }
        NamespacedKey currentPageKey = new NamespacedKey(GaiasFindings.getInstance(), "currentPage");

        if(!bordeItem.getItemMeta().getPersistentDataContainer().has(currentPageKey)) {
            return;
        }

        int currentPage = bordeItem.getItemMeta().getPersistentDataContainer().get(
            currentPageKey, PersistentDataType.INTEGER
        );

        if(!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) {
            return;
        }
        NamespacedKey goToPage = new NamespacedKey(GaiasFindings.getInstance(), "goToPage");
        NamespacedKey typeKey = new NamespacedKey(GaiasFindings.getInstance(), "menuRuneType");

        // Clic en flecha de cambio de página
        ItemMeta meta = item.getItemMeta();
        if(meta.getPersistentDataContainer().has(goToPage)) {
            int page = meta.getPersistentDataContainer().get(goToPage, PersistentDataType.INTEGER);
            try{
                openMenu(player, page);
            } catch(Exception e){
                player.sendMessage(GaiasFindings.messages.pageNotFound);
            }
        }

        // Clic en runa del menú
        if(meta.getPersistentDataContainer().has(typeKey)) {
            String runeId = meta.getPersistentDataContainer().get(typeKey, PersistentDataType.STRING);
            Rune rune = GaiasFindings.config.runes.get(runeId);
            SackRune clickedRune = GaiasFindings.utilities.getSackRune(player, runeId);
            if(clickedRune == null) {
                event.setCancelled(true);
                return;
            }

            NamespacedKey amountKey = new NamespacedKey(GaiasFindings.getInstance(), "menuAmount");
            int amount = meta.getPersistentDataContainer().get(amountKey, PersistentDataType.INTEGER);

            if(amount - 1 <= 0) {
                GaiasFindings.playerSacks.get(player).remove(clickedRune);
            } else {
                clickedRune.setAmount(clickedRune.getAmount() - 1);
            }

            try {
                event.setCancelled(true);
                GaiasFindings.utilities.addItemToInventory(player, new RuneItem(rune));
                openMenu(player, currentPage);
            } catch(Exception e){
                player.sendMessage(GaiasFindings.messages.pageNotFound);
                player.closeInventory();
            }
        }

        // Clic en runa del inventario
        NamespacedKey runeTypeKey = new NamespacedKey(GaiasFindings.getInstance(), "runeType");
        if(meta.getPersistentDataContainer().has(runeTypeKey)) {
            String runeId = meta.getPersistentDataContainer().get(runeTypeKey, PersistentDataType.STRING);
            Rune rune = GaiasFindings.config.runes.get(runeId);
            if(rune == null) {
                event.setCancelled(true);
                return;
            }

            int amount = item.getAmount();
            SackRune sackRune = GaiasFindings.utilities.getSackRune(player, rune.getId());
            if(sackRune == null) {
                sackRune = new SackRune(rune, amount);
                GaiasFindings.playerSacks.get(player).add(sackRune);
            } else {
                sackRune.setAmount(sackRune.getAmount() + amount);
            }

            player.playSound(player, GaiasFindings.config.addRuneToSackSound, 1f, 1f);

            try {
                event.setCancelled(true);
                item.setAmount(0);
                event.setCurrentItem(item);
                openMenu(player, currentPage);
            } catch(Exception e){
                player.sendMessage(GaiasFindings.messages.pageNotFound);
                player.closeInventory();
            }
        }

        event.setCancelled(true);
    }
}
