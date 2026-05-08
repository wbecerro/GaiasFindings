package wbe.gaiasFindings.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerReceiveRuneEvent extends Event implements Cancellable {

    private Player player;

    private Block block;

    private wbe.gaiasFindings.config.Block blockType;

    private boolean isCancelled = false;

    private static final HandlerList handlers = new HandlerList();

    public PlayerReceiveRuneEvent(Player player, wbe.gaiasFindings.config.Block blockType, Block block) {
        this.player = player;
        this.blockType = blockType;
        this.block = block;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public wbe.gaiasFindings.config.Block getBlockType() {
        return blockType;
    }

    public void setBlockType(wbe.gaiasFindings.config.Block blockType) {
        this.blockType = blockType;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
