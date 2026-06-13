package wbe.gaiasFindings.items;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.gaiasFindings.GaiasFindings;
import wbe.gaiasFindings.config.SackRune;

import java.util.ArrayList;

public class MenuRune extends ItemStack {

    public MenuRune(SackRune sackRune) {
        super(sackRune.getRune().getMaterial());

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(sackRune.getRune().getMaterial());
        }

        meta.setDisplayName(GaiasFindings.config.menuRuneName.replace("%rune%",
                sackRune.getRune().getName()));

        ArrayList<String> lore = new ArrayList<>();
        String enchant = sackRune.getRune().getEnchantment().getKey().getKey().toLowerCase().replace("_", " ");
        for(String loreLine : GaiasFindings.config.menuRuneLore) {
            lore.add(loreLine.replace("&", "§")
                    .replace("%enchant%", enchant)
                    .replace("%amount%", String.valueOf(sackRune.getAmount())));
        }

        meta.setLore(lore);

        NamespacedKey amountKey = new NamespacedKey(GaiasFindings.getInstance(), "menuAmount");
        meta.getPersistentDataContainer().set(amountKey, PersistentDataType.INTEGER, sackRune.getAmount());

        NamespacedKey typeKey = new NamespacedKey(GaiasFindings.getInstance(), "menuRuneType");
        meta.getPersistentDataContainer().set(typeKey, PersistentDataType.STRING, sackRune.getRune().getId());

        meta.addEnchant(Enchantment.INFINITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        setItemMeta(meta);
    }
}
