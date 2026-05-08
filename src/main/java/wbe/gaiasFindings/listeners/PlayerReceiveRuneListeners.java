package wbe.gaiasFindings.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wbe.gaiasFindings.GaiasFindings;
import wbe.gaiasFindings.config.Block;
import wbe.gaiasFindings.events.PlayerReceiveRuneEvent;

import java.util.Random;

public class PlayerReceiveRuneListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void manageGemDistribution(PlayerReceiveRuneEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Random random = new Random();
        double doubleChance = GaiasFindings.utilities.getPlayerDoubleChance(player);
        Block brokenBlock = event.getBlockType();

        player.playSound(player.getLocation(), GaiasFindings.config.runeDropSound, 1F, 1F);
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), GaiasFindings.utilities.generateGemstone(brokenBlock.getRandomReward()));
        if(random.nextDouble(100) <= doubleChance) {
            player.sendMessage(GaiasFindings.messages.doubleDrop);
            player.playSound(player.getLocation(), GaiasFindings.config.doubleDropSound, 1F, 1F);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), GaiasFindings.utilities.generateGemstone(brokenBlock.getRandomReward()));
        }
    }
}
