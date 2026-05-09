package wbe.gaiasFindings.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.gaiasFindings.GaiasFindings;
import wbe.gaiasFindings.config.Rune;

import java.util.*;

public class Utilities {

    private GaiasFindings plugin;

    public Utilities() {
        plugin = GaiasFindings.getInstance();
    }

    public void addRuneChance(ItemStack item, double chance) {
        NamespacedKey baseItemKey = new NamespacedKey(plugin, "runeChance");
        String loreLine = GaiasFindings.config.shovelRuneChance
                .replace("%rune_chance%", String.valueOf(chance));
        ItemMeta meta = item.getItemMeta();

        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        List<String> lore = new ArrayList<>();
        if(meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add(loreLine);
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(baseItemKey, PersistentDataType.DOUBLE, chance);
        item.setItemMeta(meta);
    }

    public void addDoubleDropChance(ItemStack item, double chance) {
        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "doubleChance");
        String loreLine = GaiasFindings.config.shovelDoubleChance
                .replace("%double_chance%", String.valueOf(chance));
        ItemMeta meta = item.getItemMeta();

        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        List<String> lore = new ArrayList<>();
        if(meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add(loreLine);
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(baseDoubleKey, PersistentDataType.DOUBLE, chance);
        item.setItemMeta(meta);
    }

    private double getItemRuneChance(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return 0;
        }

        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "runeChance");
        if(meta.getPersistentDataContainer().has(baseDoubleKey)) {
            return meta.getPersistentDataContainer().get(baseDoubleKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public double getPlayerRuneChance(Player player) {
        double chance = GaiasFindings.config.baseRuneChance;

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        ItemStack[] armor = inventory.getArmorContents();

        if(!mainHand.getType().equals(Material.AIR)) {
            chance += getItemRuneChance(mainHand);
        }

        if(!offHand.getType().equals(Material.AIR)) {
            chance += getItemRuneChance(offHand);
        }

        for(ItemStack item : armor) {
            if(item == null) {
                continue;
            }
            chance += getItemRuneChance(item);
        }

        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "runeChance");
        if(player.getPersistentDataContainer().has(baseDoubleKey)) {
            chance += player.getPersistentDataContainer().get(baseDoubleKey, PersistentDataType.DOUBLE);
        }

        return chance;
    }

    private double getItemDoubleChance(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return 0;
        }

        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "doubleChance");
        if(meta.getPersistentDataContainer().has(baseDoubleKey)) {
            return meta.getPersistentDataContainer().get(baseDoubleKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public double getPlayerDoubleChance(Player player) {
        double chance = GaiasFindings.config.baseDoubleChance;

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        ItemStack[] armor = inventory.getArmorContents();

        if(!mainHand.getType().equals(Material.AIR)) {
            chance += getItemDoubleChance(mainHand);
        }

        if(!offHand.getType().equals(Material.AIR)) {
            chance += getItemDoubleChance(offHand);
        }

        for(ItemStack item : armor) {
            if(item == null) {
                continue;
            }
            chance += getItemDoubleChance(item);
        }

        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "doubleChance");
        if(player.getPersistentDataContainer().has(baseDoubleKey)) {
            chance += player.getPersistentDataContainer().get(baseDoubleKey, PersistentDataType.DOUBLE);
        }

        return chance;
    }

    public boolean checkItem(ItemStack item, NamespacedKey key) {
        if(item == null || item.getType().equals(Material.AIR)) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return false;
        }

        if(!meta.getPersistentDataContainer().has(key)) {
            return false;
        }

        return true;
    }

    /**
     * Método para añadir probabilidad de runas o doble recompensas a un jugador.
     *
     * @param player Jugador al que añadir
     * @param chance Probabiliad a añadir
     * @param type Tipo de probabilidad 0 -> runas, 1 -> doble
     */
    public void addChanceToPlayer(Player player, double chance, int type) {
        NamespacedKey key;
        switch(type) {
            case 0:
                key = new NamespacedKey(plugin, "runeChance");
                break;
            default:
                key = new NamespacedKey(plugin, "doubleChance");
                break;
        }

        if(player.getPersistentDataContainer().has(key)) {
            double playerChance = player.getPersistentDataContainer().get(key, PersistentDataType.DOUBLE);
            playerChance += chance;
            player.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, playerChance);
        } else {
            player.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, chance);
        }
    }

    /**
     * Método para quitar probabilidad de runas o doble recompensas a un jugador.
     *
     * @param player Jugador al que quitar
     * @param chance Probabiliad a quitar
     * @param type Tipo de probabilidad 0 -> runas, 1 -> doble
     */
    public void removeChanceFromPlayer(Player player, double chance, int type) {
        NamespacedKey key;
        switch(type) {
            case 0:
                key = new NamespacedKey(plugin, "runeChance");
                break;
            default:
                key = new NamespacedKey(plugin, "doubleChance");
                break;
        }

        if(!player.getPersistentDataContainer().has(key)) {
            return;
        }

        double playerChance = player.getPersistentDataContainer().get(key, PersistentDataType.DOUBLE);
        playerChance -= chance;
        player.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, playerChance);
    }

    public void addItemToInventory(Player player, ItemStack item) {
        if(player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), item);
        } else {
            player.getInventory().addItem(item);
        }
    }

    public boolean applyRune(Rune rune, ItemStack item, Player player) {
        if(!item.getItemMeta().hasEnchant(rune.getEnchantment())) {
            player.sendMessage(GaiasFindings.messages.enchantmentNotPresent);
            return false;
        }

        if(player.getLevel() < GaiasFindings.config.applyCost) {
            player.sendMessage(GaiasFindings.messages.notEnoughXP.replace("%levels%", String.valueOf(GaiasFindings.config.applyCost)));
            return false;
        }

        NamespacedKey runeKey = new NamespacedKey(plugin, "appliedRune" + rune.getId());
        int uses = 0;
        if(item.getItemMeta().getPersistentDataContainer().has(runeKey)) {
            if(item.getItemMeta().getPersistentDataContainer().get(runeKey, PersistentDataType.INTEGER) >= GaiasFindings.config.maxUsesPerRune) {
                player.sendMessage(GaiasFindings.messages.maxRunes);
                return false;
            }
            uses = item.getItemMeta().getPersistentDataContainer().get(runeKey, PersistentDataType.INTEGER);
        }
        ItemMeta meta = item.getItemMeta();

        int enchantLevel = meta.getEnchantLevel(rune.getEnchantment()) + 1;
        meta.addEnchant(rune.getEnchantment(), enchantLevel, true);
        meta.getPersistentDataContainer().set(runeKey, PersistentDataType.INTEGER, uses + 1);
        player.setLevel(player.getLevel() - GaiasFindings.config.applyCost);
        item.setItemMeta(meta);

        player.playSound(player.getLocation(), GaiasFindings.config.addRuneSound, 1F, 1F);
        player.sendMessage(GaiasFindings.messages.runeApplied);
        return true;
    }
}
