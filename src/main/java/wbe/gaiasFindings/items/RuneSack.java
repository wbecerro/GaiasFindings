package wbe.gaiasFindings.items;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.gaiasFindings.GaiasFindings;

import java.util.ArrayList;

public class RuneSack extends ItemStack {

    public RuneSack() {
        super(GaiasFindings.config.sackMaterial);

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(GaiasFindings.config.sackMaterial);
        }

        meta.setDisplayName(GaiasFindings.config.sackName);

        ArrayList<String> lore = new ArrayList<>();
        for(String line : GaiasFindings.config.sackLore) {
            lore.add(line.replace("&", "§"));
        }
        meta.setLore(lore);

        NamespacedKey sackKey = new NamespacedKey(GaiasFindings.getInstance(), "runesack");
        meta.getPersistentDataContainer().set(sackKey, PersistentDataType.BOOLEAN, true);

        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);

        if(GaiasFindings.config.sackGlow) {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        setItemMeta(meta);
    }
}
