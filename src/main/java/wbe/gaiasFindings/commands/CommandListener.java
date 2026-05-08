package wbe.gaiasFindings.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wbe.gaiasFindings.GaiasFindings;
import wbe.gaiasFindings.config.Rune;
import wbe.gaiasFindings.items.RuneItem;
import wbe.gaiasFindings.items.Shovel;

public class CommandListener implements CommandExecutor {

    private GaiasFindings plugin;

    public CommandListener() {
        plugin = GaiasFindings.getInstance();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("GaiasFindings")) {
            Player player = null;
            if(sender instanceof Player) {
                player = (Player) sender;
            }

            if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
                if(!sender.hasPermission("gaiasfindings.command.help")) {
                    sender.sendMessage(GaiasFindings.messages.noPermission);
                    return false;
                }

                for(String line : GaiasFindings.messages.help) {
                    sender.sendMessage(line.replace("&", "§"));
                }
            } else if(args[0].equalsIgnoreCase("stats")) {
                if(!sender.hasPermission("gaiasfindings.command.stats")) {
                    sender.sendMessage(GaiasFindings.messages.noPermission);
                    return false;
                }

                String gemChance = String.valueOf(GaiasFindings.utilities.getPlayerRuneChance(player));
                String doubleChance = String.valueOf(GaiasFindings.utilities.getPlayerDoubleChance(player));
                for(String line : GaiasFindings.messages.stats) {
                    sender.sendMessage(line.replace("&", "§")
                            .replace("%runeChance%", gemChance)
                            .replace("%doubleChance%", doubleChance));
                }
            } else if(args[0].equalsIgnoreCase("shovel")) {
                if(!sender.hasPermission("gaiasfindings.command.shovel")) {
                    sender.sendMessage(GaiasFindings.messages.noPermission);
                    return false;
                }

                if(args.length < 3) {
                    sender.sendMessage(GaiasFindings.messages.notEnoughArgs);
                    sender.sendMessage(GaiasFindings.messages.shovelArguments);
                    return false;
                }

                Material material;
                try {
                    material = Material.valueOf(args[1].toUpperCase());
                } catch(IllegalArgumentException ex) {
                    sender.sendMessage(GaiasFindings.messages.invalidMaterial);
                    return false;
                }
                double runeChance = Double.valueOf(args[2]);

                Shovel shovel = new Shovel(material, runeChance);
                if(args.length > 3) {
                    player = Bukkit.getPlayer(args[3]);
                }

                GaiasFindings.utilities.addItemToInventory(player, shovel);
                player.sendMessage(GaiasFindings.messages.shovelGiven);
            } else if(args[0].equalsIgnoreCase("list")) {
                if(!sender.hasPermission("gaiasfindings.command.list")) {
                    sender.sendMessage(GaiasFindings.messages.noPermission);
                    return false;
                }

                sender.sendMessage(GaiasFindings.messages.list + GaiasFindings.config.runes.keySet().toString());
            } else if(args[0].equalsIgnoreCase("rune")) {
                if(!sender.hasPermission("gaiasfindings.command.rune")) {
                    sender.sendMessage(GaiasFindings.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(GaiasFindings.messages.notEnoughArgs);
                    sender.sendMessage(GaiasFindings.messages.runeArguments);
                    return false;
                }

                Rune rune = GaiasFindings.config.runes.get(args[1]);
                RuneItem runeItem = new RuneItem(rune);
                if(args.length > 2) {
                    player = Bukkit.getPlayer(args[2]);
                }

                GaiasFindings.utilities.addItemToInventory(player, runeItem);
                player.sendMessage(GaiasFindings.messages.runeGiven.replace("%rune%", rune.getName()));
            } else if(args[0].equalsIgnoreCase("runeChance")) {
                if(!sender.hasPermission("gaiasfindings.command.runeChance")) {
                    sender.sendMessage(GaiasFindings.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(GaiasFindings.messages.notEnoughArgs);
                    sender.sendMessage(GaiasFindings.messages.runeChanceArguments);
                    return false;
                }

                GaiasFindings.utilities.addRuneChance(player.getInventory().getItemInMainHand(), Double.valueOf(args[1]));
                player.sendMessage(GaiasFindings.messages.runeChanceAdded);
            } else if(args[0].equalsIgnoreCase("double")) {
                if(!sender.hasPermission("gaiasfindings.command.double")) {
                    sender.sendMessage(GaiasFindings.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(GaiasFindings.messages.notEnoughArgs);
                    sender.sendMessage(GaiasFindings.messages.doubleArguments);
                    return false;
                }

                GaiasFindings.utilities.addDoubleDropChance(player.getInventory().getItemInMainHand(), Double.valueOf(args[1]));
                player.sendMessage(GaiasFindings.messages.doubleAdded);
            } else if(args[0].equalsIgnoreCase("reload")) {
                if(!sender.hasPermission("gaiasfindings.command.reload")) {
                    sender.sendMessage(GaiasFindings.messages.noPermission);
                    return false;
                }

                plugin.reloadConfiguration();
                sender.sendMessage(GaiasFindings.messages.reload);
            }
        }
        return true;
    }
}
