package de.heldenplugin.main.listeners;

import de.heldenplugin.main.HeldenPlugin;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class StarterItemSetupListener
implements Listener {
    private final HeldenPlugin plugin;

    public StarterItemSetupListener(HeldenPlugin heldenMod) {
        this.plugin = heldenMod;
    }

    @EventHandler
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        if (!(inventoryClickEvent.getWhoClicked() instanceof Player)) {
            return;
        }
        if (!inventoryClickEvent.getView().getTitle().equals("\u00a78[ \u00a7eStarter-Items festlegen \u00a78]")) {
            return;
        }
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        int n = inventoryClickEvent.getRawSlot();
        if (n >= 27 && n <= 53) {
            inventoryClickEvent.setCancelled(true);
            if (n == 40) {
                this.save(player, inventoryClickEvent.getInventory());
                player.closeInventory();
                player.sendMessage("\u00a7a[Setup] \u00a7fGespeichert!");
            }
            if (n == 44) {
                this.plugin.getStarterItemManager().setStarterItems(new ArrayList<ItemStack>());
                player.closeInventory();
                player.sendMessage("\u00a7c[Setup] \u00a7fGel\u00f6scht!");
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent inventoryCloseEvent) {
        if (!(inventoryCloseEvent.getPlayer() instanceof Player)) {
            return;
        }
        if (!inventoryCloseEvent.getView().getTitle().equals("\u00a78[ \u00a7eStarter-Items festlegen \u00a78]")) {
            return;
        }
        this.save((Player)inventoryCloseEvent.getPlayer(), inventoryCloseEvent.getInventory());
    }

    private void save(Player player, Inventory inventory) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        for (int i = 0; i < 27; ++i) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null || itemStack.getType() == Material.AIR || itemStack.getType() == Material.GRAY_STAINED_GLASS_PANE || itemStack.getType() == Material.LIME_WOOL || itemStack.getType() == Material.RED_WOOL || itemStack.getType() == Material.PAPER) continue;
            arrayList.add(itemStack.clone());
        }
        this.plugin.getStarterItemManager().setStarterItems(arrayList);
    }
}

