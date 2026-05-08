package wbe.gaiasFindings.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.gaiasFindings.GaiasFindings;

import java.util.ArrayList;

public class Shovel extends ItemStack {

    public Shovel(Material material, double runeChance) {
        super(material);

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(material);
        }

        meta.setDisplayName(GaiasFindings.config.shovelName);

        ArrayList<String> lore = new ArrayList<>();
        for(String line : GaiasFindings.config.shovelLore) {
            lore.add(line.replace("&", "§"));
        }

        lore.add(GaiasFindings.config.shovelRuneChance.replace("%rune_chance%", String.valueOf(runeChance)));
        meta.setLore(lore);

        NamespacedKey runeKey = new NamespacedKey(GaiasFindings.getInstance(), "runeChance");
        meta.getPersistentDataContainer().set(runeKey, PersistentDataType.DOUBLE, runeChance);

        NamespacedKey fortuneKey = new NamespacedKey(GaiasFindings.getInstance(), "fortuneLevel");
        meta.getPersistentDataContainer().set(fortuneKey, PersistentDataType.INTEGER, 0);

        setItemMeta(meta);
    }
}
