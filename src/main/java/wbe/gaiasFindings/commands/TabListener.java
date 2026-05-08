package wbe.gaiasFindings.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import wbe.gaiasFindings.GaiasFindings;
import wbe.gaiasFindings.config.Rune;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabListener implements TabCompleter {

    private final List<String> subCommands = Arrays.asList("help", "shovel", "list", "rune",
            "runeChance", "double", "stats", "reload");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if(!command.getName().equalsIgnoreCase("GaiasFindings")) {
            return completions;
        }

        // Mostrar subcomandos
        if(args.length == 1) {
            StringUtil.copyPartialMatches(args[0], subCommands, completions);
        }

        // Argumento 1
        if(args.length == 2) {
            switch(args[0].toLowerCase()) {
                case "shovel":
                    List<String> materials = new ArrayList<>();
                    Arrays.asList(Material.values()).forEach((material -> {
                        if(args[1].isEmpty()) {
                            materials.add(material.toString());
                        } else if(material.toString().startsWith(args[1])) {
                            materials.add(material.toString());
                        }
                    }));
                    completions.addAll(materials);
                    break;
                case "rune":
                    for(Rune rune : GaiasFindings.config.runeList) {
                        if(args[1].isEmpty()) {
                            completions.add(rune.getId());
                        } else if(rune.getId().startsWith(args[1])) {
                            completions.add(rune.getId());
                        }
                    }
                    break;
                case "runechance":
                case "double":
                    completions.add("<Probabilidad>");
                    break;
            }
        }

        // Argumento 2
        if(args.length == 3) {
            switch(args[0].toLowerCase()) {
                case "shovel":
                    completions.add("<Probabilidad runa>");
                    break;
                case "rune":
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        if(args[2].isEmpty()) {
                            completions.add(player.getName());
                        } else if(player.getName().startsWith(args[2])) {
                            completions.add(player.getName());
                        }
                    }
                    break;
            }
        }

        // Argumento 3
        if(args.length == 4) {
            switch(args[0].toLowerCase()) {
                case "shovel":
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        if(args[3].isEmpty()) {
                            completions.add(player.getName());
                        } else if(player.getName().startsWith(args[3])) {
                            completions.add(player.getName());
                        }
                    }
                    break;
            }
        }

        return completions;
    }
}
