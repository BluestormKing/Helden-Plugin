package de.heldenplugin.main.listeners;

import de.heldenplugin.main.HeldenPlugin;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockProtectionListener
implements Listener {
    private final HeldenPlugin plugin;

    public BlockProtectionListener(HeldenPlugin heldenMod) {
        this.plugin = heldenMod;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent blockBreakEvent) {
        Player player = blockBreakEvent.getPlayer();
        if (player.isOp()) {
            return;
        }
        Location location = blockBreakEvent.getBlock().getLocation();
        UUID uUID = player.getUniqueId();
        if (this.plugin.getBaseManager().isOwnBase(location, uUID)) {
            return;
        }
        if (this.plugin.getBaseManager().isInForeignBase(location, uUID)) {
            blockBreakEvent.setCancelled(true);
            player.sendMessage("\u00a7c[Schutz] \u00a7fKein Abbauen in fremder Basis!");
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent blockPlaceEvent) {
        Player player = blockPlaceEvent.getPlayer();
        if (player.isOp()) {
            return;
        }
        Location location = blockPlaceEvent.getBlock().getLocation();
        UUID uUID = player.getUniqueId();
        if (this.plugin.getBaseManager().isOwnBase(location, uUID)) {
            return;
        }
        if (this.plugin.getBaseManager().isInForeignBase(location, uUID)) {
            blockPlaceEvent.setCancelled(true);
            player.sendMessage("\u00a7c[Schutz] \u00a7fKein Bauen in fremder Basis!");
        }
    }
}

