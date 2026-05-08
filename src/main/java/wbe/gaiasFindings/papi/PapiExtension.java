package wbe.gaiasFindings.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class PapiExtension extends PlaceholderExpansion {

    @Override
    public String getAuthor() {
        return "wbe";
    }

    @Override
    public String getIdentifier() {
        return "TartarusRiches";
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


        return null;
    }
}
