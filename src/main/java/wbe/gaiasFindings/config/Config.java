package wbe.gaiasFindings.config;

import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Config {

    private FileConfiguration config;

    public double baseRuneChance;
    public double baseDoubleChance;
    public int applyCost;

    public Sound doubleDropSound;
    public Sound runeDropSound;
    public Sound addRuneSound;
    public Sound changeModeSound;

    public String shovelName;
    public List<String> shovelLore = new ArrayList<>();
    public String shovelRuneChance;
    public String shovelDoubleChance;

    public String runeName;
    public List<String> runeLore = new ArrayList<>();
    public boolean runeGlow;

    public HashMap<String, Rune> runes = new HashMap<>();
    public List<Rune> runeList = new ArrayList<>();
    public List<Block> blocks = new ArrayList<>();
    private int totalweight;

    public List<Enchantment> enchantments = new ArrayList<>();

    public Config(FileConfiguration config) {
        this.config = config;

        baseRuneChance = config.getDouble("Config.baseRuneChance");
        baseDoubleChance = config.getDouble("Config.baseDoubleChance");
        applyCost = config.getInt("Config.applyLevelCost");

        doubleDropSound = Sound.valueOf(config.getString("Sounds.doubleDropSound"));
        runeDropSound = Sound.valueOf(config.getString("Sounds.runeDropSound"));
        addRuneSound = Sound.valueOf(config.getString("Sounds.addRuneSound"));
        changeModeSound = Sound.valueOf(config.getString("Sounds.changeModeSound"));

        shovelName = config.getString("Items.shovel.name").replace("&", "§");
        shovelLore = config.getStringList("Items.shovel.lore");
        shovelRuneChance = config.getString("Items.shovel.runeChance").replace("&", "§");
        shovelDoubleChance = config.getString("Items.shovel.doubleChance").replace("&", "§");

        runeName = config.getString("Items.rune.name").replace("&", "§");
        runeLore = config.getStringList("Items.rune.lore");
        runeGlow = config.getBoolean("Items.rune.glow");

        Registry.ENCHANTMENT.iterator().forEachRemaining(enchantments::add);

        loadRunes();
        loadBlocks();
    }

    private void loadRunes() {
        Set<String> configRunes = config.getConfigurationSection("Runes").getKeys(false);
        for(String rune : configRunes) {
            String id = rune;
            Material material = Material.valueOf(config.getString("Runes." + rune + ".material"));
            String name = config.getString("Runes." + rune + ".name").replace("&", "§");
            Enchantment enchantment = searchEnchantmentByName(config.getString("Runes." + rune + ".enchantment"));
            if(enchantment == null) {
                continue;
            }

            Rune newRune = new Rune(id, material, name, enchantment);
            runes.put(id, newRune);
            runeList.add(newRune);
        }
    }

    private void loadBlocks() {
        Set<String> configBlocks = config.getConfigurationSection("Blocks").getKeys(false);
        for(String block : configBlocks) {
            Material material = Material.valueOf(block);
            HashMap<Rune, Integer> rewards = loadRewards(block);
            blocks.add(new Block(material, rewards, totalweight));
        }
    }

    private HashMap<Rune, Integer> loadRewards(String block) {
        totalweight = 0;
        Set<String> configRewards = config.getConfigurationSection("Blocks." + block + ".rewards").getKeys(false);
        HashMap<Rune, Integer> rewards = new HashMap<>();
        for(String reward : configRewards) {
            Rune rune = runes.get(reward);
            int weight = config.getInt("Blocks." + block + ".rewards." + reward + ".weight");
            totalweight += weight;
            rewards.put(rune, weight);
        }

        return rewards;
    }

    private Enchantment searchEnchantmentByName(String name) {
        for(Enchantment enchantment : enchantments) {
            if(enchantment.getKey() != null && enchantment.getKey().getKey().equalsIgnoreCase(name)) {
                return enchantment;
            }
        }

        return null;
    }
}
