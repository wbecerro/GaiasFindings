package wbe.gaiasFindings.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.gaiasFindings.GaiasFindings;

public class PlayerDropItemListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void switchEnchantmentOnDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(!player.isSneaking()) {
            return;
        }

        ItemStack droppedItem = event.getItemDrop().getItemStack();
        if(droppedItem.getType().equals(Material.AIR)) {
            return;
        }

        ItemMeta meta = droppedItem.getItemMeta();
        if(meta == null) {
            return;
        }

        NamespacedKey fortuneKey = new NamespacedKey(GaiasFindings.getInstance(), "fortuneLevel");
        if(!meta.getPersistentDataContainer().has(fortuneKey)) {
            return;
        }

        if(meta.hasEnchant(Enchantment.FORTUNE)) {
            int fortuneLevel = meta.getEnchantLevel(Enchantment.FORTUNE);
            meta.getPersistentDataContainer().set(fortuneKey, PersistentDataType.INTEGER, fortuneLevel);
            meta.removeEnchant(Enchantment.FORTUNE);
            meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        } else if(meta.hasEnchant(Enchantment.SILK_TOUCH)) {
            int fortuneLevel = meta.getPersistentDataContainer().get(fortuneKey, PersistentDataType.INTEGER);
            meta.removeEnchant(Enchantment.SILK_TOUCH);
            meta.addEnchant(Enchantment.FORTUNE, fortuneLevel, true);
        }

        droppedItem.setItemMeta(meta);

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(GaiasFindings.messages.modeChanged));
        player.playSound(player.getLocation(), GaiasFindings.config.changeModeSound, 1F, 1F);
        event.setCancelled(true);
    }
}
