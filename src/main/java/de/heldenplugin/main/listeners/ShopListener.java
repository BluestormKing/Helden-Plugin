package de.heldenplugin.main.listeners;

import de.heldenplugin.main.HeldenPlugin;
import de.heldenplugin.main.manager.ShopGUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ShopListener
implements Listener {
    private final HeldenPlugin plugin;

    public ShopListener(HeldenPlugin heldenMod) {
        this.plugin = heldenMod;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent) {
        if (playerInteractEvent.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Block block = playerInteractEvent.getClickedBlock();
        if (block == null || block.getType() != Material.NOTE_BLOCK) {
            return;
        }
        Location location = this.plugin.getShopManager().getShopLocation();
        if (location == null) {
            return;
        }
        Location location2 = block.getLocation();
        if (!location2.getWorld().equals((Object)location.getWorld()) || location2.distance(location) > 1.0) {
            return;
        }
        playerInteractEvent.setCancelled(true);
        ShopGUI.openMain(playerInteractEvent.getPlayer());
    }

    @EventHandler
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        if (!(inventoryClickEvent.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        String string = inventoryClickEvent.getView().getTitle();
        if (string.equals("\u00a78\u00a7l[ \u00a7a\u00a7lSHOP \u00a78\u00a7l]") || string.startsWith("\u00a78[ \u00a7aShop: ")) {
            inventoryClickEvent.setCancelled(true);
            if (inventoryClickEvent.getCurrentItem() == null || inventoryClickEvent.getCurrentItem().getType() == Material.AIR) {
                return;
            }
            ShopGUI.handleClick(player, inventoryClickEvent.getInventory(), string, inventoryClickEvent.getRawSlot(), inventoryClickEvent.isRightClick());
        }
    }
}

