package wbe.gaiasFindings.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import wbe.gaiasFindings.GaiasFindings;

public class PapiExtension extends PlaceholderExpansion {

    @Override
    public String getAuthor() {
        return "wbe";
    }

    @Override
    public String getIdentifier() {
        return "GaiasFindings";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("runeChance")) {
            return String.valueOf(GaiasFindings.utilities.getPlayerRuneChance(player.getPlayer()));
        } else if(params.equalsIgnoreCase("doubleChance")) {
            return String.valueOf(GaiasFindings.utilities.getPlayerDoubleChance(player.getPlayer()));
        }

        return null;
    }
}
