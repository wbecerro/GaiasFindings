package wbe.gaiasFindings.listeners;

import com.gmail.nossr50.mcMMO;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import wbe.gaiasFindings.GaiasFindings;
import wbe.gaiasFindings.config.Block;
import wbe.gaiasFindings.events.PlayerReceiveRuneEvent;

import java.util.Random;

public class BlockBreakListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void giveRunesOnBreakingBlocks(BlockBreakEvent event) {
        if(event.isCancelled()) {
            return;
        }

        if(mcMMO.getUserBlockTracker().isIneligible(event.getBlock().getState())) {
            return;
        }

        Material material = event.getBlock().getType();
        Block brokenBlock = null;
        for(Block block : GaiasFindings.config.blocks) {
            if(block.getId().equals(material)) {
                brokenBlock = block;
            }
        }

        if(brokenBlock == null) {
            return;
        }

        Random random = new Random();
        Player player = event.getPlayer();
        double runeChance = GaiasFindings.utilities.getPlayerRuneChance(player);

        if(random.nextDouble(100) <= runeChance) {
            GaiasFindings.getInstance().getServer().getPluginManager().callEvent(new PlayerReceiveRuneEvent(player, brokenBlock, event.getBlock()));
        }
    }
}
