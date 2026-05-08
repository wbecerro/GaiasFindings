package wbe.gaiasFindings.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Messages {

    public String noPermission;
    public String notEnoughArgs;
    public String reload;
    public String list;
    public String doubleDrop;
    public String shovelGiven;
    public String invalidMaterial;
    public String shovelArguments;
    public String runeGiven;
    public String runeArguments;
    public String runeChanceAdded;
    public String runeChanceArguments;
    public String doubleAdded;
    public String doubleArguments;
    public String runeApplied;
    public String enchantmentNotPresent;
    public String maxRunes;
    public String notEnoughXP;
    public String modeChanged;
    public List<String> stats;
    public List<String> help;

    public Messages(FileConfiguration config) {
        noPermission = config.getString("Messages.noPermission").replace("&", "§");
        notEnoughArgs = config.getString("Messages.notEnoughArgs").replace("&", "§");
        reload = config.getString("Messages.reload").replace("&", "§");
        list = config.getString("Messages.list").replace("&", "§");
        doubleDrop = config.getString("Messages.doubleDrop").replace("&", "§");
        shovelGiven = config.getString("Messages.shovelGiven").replace("&", "§");
        invalidMaterial = config.getString("Messages.invalidMaterial").replace("&", "§");
        shovelArguments = config.getString("Messages.shovelArguments").replace("&", "§");
        runeGiven = config.getString("Messages.runeGiven").replace("&", "§");
        runeArguments = config.getString("Messages.runeArguments").replace("&", "§");
        runeChanceAdded = config.getString("Messages.runeChanceAdded").replace("&", "§");
        runeChanceArguments = config.getString("Messages.runeChanceArguments").replace("&", "§");
        doubleAdded = config.getString("Messages.doubleAdded").replace("&", "§");
        doubleArguments = config.getString("Messages.doubleArguments").replace("&", "§");
        runeApplied = config.getString("Messages.runeApplied").replace("&", "§");
        enchantmentNotPresent = config.getString("Messages.enchantmentNotPresent").replace("&", "§");
        maxRunes = config.getString("Messages.maxRunes").replace("&", "§");
        notEnoughXP = config.getString("Messages.notEnoughXP").replace("&", "§");
        modeChanged = config.getString("Messages.modeChanged").replace("&", "§");
        stats = config.getStringList("Messages.stats");
        help = config.getStringList("Messages.help");
    }
}