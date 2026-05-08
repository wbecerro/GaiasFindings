package wbe.gaiasFindings.items;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.gaiasFindings.GaiasFindings;
import wbe.gaiasFindings.config.Rune;

import java.util.ArrayList;

public class RuneItem extends ItemStack {

    public RuneItem(Rune rune) {
        super(rune.getMaterial());

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(rune.getMaterial());
        }

        meta.setDisplayName(GaiasFindings.config.runeName.replace("%rune_name%", rune.getName()));

        ArrayList<String> lore = new ArrayList<>();
        String enchant = rune.getEnchantment().getKey().getKey().toLowerCase().replace("_", " ");
        for(String line : GaiasFindings.config.runeLore) {
            lore.add(line.replace("&", "§").replace("%enchant%", enchant));
        }

        meta.setLore(lore);
        NamespacedKey typeKey = new NamespacedKey(GaiasFindings.getInstance(), "runeType");
        meta.getPersistentDataContainer().set(typeKey, PersistentDataType.STRING, rune.getId());

        if(GaiasFindings.config.runeGlow) {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        setItemMeta(meta);
    }
}
