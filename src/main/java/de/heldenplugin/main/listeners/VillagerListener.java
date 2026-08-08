package de.heldenplugin.main.listeners;

import de.heldenplugin.main.HeldenPlugin;
import de.heldenplugin.main.manager.ShopGUI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VillagerListener implements Listener {

    public static final String CLAIM_TITLE = "\u00a78[ \u00a7aVillager hinzuf\u00fcgen \u00a78]";

    private final HeldenPlugin plugin;
    private final Map<UUID, UUID> pendingClaims = new HashMap<UUID, UUID>();

    public VillagerListener(HeldenPlugin heldenMod) {
        this.plugin = heldenMod;
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getEntityType() != EntityType.VILLAGER) {
            return;
        }
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
            return;
        }
        Villager villager = (Villager) event.getEntity();
        Player nearest = null;
        double best = 10.0;
        for (Player p : event.getLocation().getWorld().getPlayers()) {
            double dist = p.getLocation().distance(event.getLocation());
            if (dist < best) {
                best = dist;
                nearest = p;
            }
        }
        if (nearest != null) {
            this.plugin.getVillagerManager().register(nearest.getUniqueId(), villager.getUniqueId());
            int count = this.plugin.getVillagerManager().getCount(nearest.getUniqueId());
            nearest.sendMessage("\u00a76[Villager] \u00a7fZugewiesen! Jetzt \u00a7a" + count + " \u00a7fVillager.");
            VillagerListener.updateSign(nearest);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Villager)) {
            return;
        }
        Villager villager = (Villager) event.getRightClicked();
        Player player = event.getPlayer();
        UUID owner = this.plugin.getVillagerManager().getOwner(villager.getUniqueId());
        if (owner == null) {
            event.setCancelled(true);
            this.openClaimGui(player, villager);
            return;
        }
        if (owner.equals(player.getUniqueId())) {
            return;
        }
        event.setCancelled(true);
        player.sendMessage("\u00a76[Villager] \u00a7fGeh\u00f6rt: \u00a7e" + this.plugin.getServer().getOfflinePlayer(owner).getName());
    }

    private void openClaimGui(Player player, Villager villager) {
        this.pendingClaims.put(player.getUniqueId(), villager.getUniqueId());
        Inventory inventory = Bukkit.createInventory(null, 9, CLAIM_TITLE);
        inventory.setItem(3, ShopGUI.make(Material.LIME_WOOL, "\u00a7a\u00a7lJa, hinzuf\u00fcgen", "\u00a77Dieser Villager geh\u00f6rt dann dir.", "\u00a77Du bekommst 5 Emeralds/4 Tage."));
        inventory.setItem(5, ShopGUI.make(Material.RED_WOOL, "\u00a7c\u00a7lNein", "\u00a77Abbrechen"));
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClaimClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        if (!CLAIM_TITLE.equals(event.getView().getTitle())) {
            return;
        }
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        UUID villagerUuid = this.pendingClaims.remove(player.getUniqueId());
        if (villagerUuid == null) {
            return;
        }
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null) {
            return;
        }
        if (clicked.getType() != Material.LIME_WOOL) {
            player.closeInventory();
            return;
        }
        if (this.plugin.getVillagerManager().getOwner(villagerUuid) != null) {
            player.sendMessage("\u00a7c[Villager] \u00a7fDieser Villager wurde bereits vergeben.");
            player.closeInventory();
            return;
        }
        this.plugin.getVillagerManager().register(player.getUniqueId(), villagerUuid);
        int count = this.plugin.getVillagerManager().getCount(player.getUniqueId());
        player.sendMessage("\u00a7a[Villager] \u00a7fHinzugef\u00fcgt! Jetzt \u00a7a" + count + " \u00a7fVillager.");
        VillagerListener.updateSign(player);
        player.closeInventory();
        this.plugin.getServer().getScheduler().runTask(this.plugin, new OpenTradeTask(player, villagerUuid));
    }

    private class OpenTradeTask implements Runnable {
        private final Player player;
        private final UUID villagerUuid;

        OpenTradeTask(Player player, UUID villagerUuid) {
            this.player = player;
            this.villagerUuid = villagerUuid;
        }

        @Override
        public void run() {
            Entity entity = Bukkit.getEntity(this.villagerUuid);
            if (entity instanceof Villager && this.player.isOnline()) {
                this.player.openMerchant((Villager) entity, true);
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Villager)) {
            return;
        }
        Villager villager = (Villager) event.getEntity();
        UUID owner = this.plugin.getVillagerManager().getOwner(villager.getUniqueId());
        if (owner == null) {
            return;
        }
        this.plugin.getVillagerManager().unregister(villager.getUniqueId());
        Player player = Bukkit.getPlayer(owner);
        if (player != null && player.isOnline()) {
            int count = this.plugin.getVillagerManager().getCount(owner);
            player.sendMessage("\u00a7c[Villager] \u00a7fEin Villager von dir ist gestorben! Jetzt noch \u00a7e" + count + " \u00a7fVillager.");
            VillagerListener.updateSign(player);
        }
    }

    @EventHandler
    public void onUseInfoSign(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }
        ItemStack itemStack = event.getItem();
        if (itemStack == null || itemStack.getType() != Material.OAK_SIGN) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null || !"\u00a76\u00a7lVillager-\u00dcbersicht".equals(itemMeta.getDisplayName())) {
            return;
        }
        event.setCancelled(true);
        Player player = event.getPlayer();
        int count = this.plugin.getVillagerManager().getCount(player.getUniqueId());
        player.sendMessage("\u00a76\u00a7l=== Villager-Info ===");
        player.sendMessage("\u00a77Deine Villager: \u00a7a" + count);
        player.sendMessage("\u00a77Einnahmen alle 4 Tage: \u00a7a" + count * 5 + " Emeralds \u00a77(5 pro Villager)");
    }

    public static void updateSign(Player player) {
        int count = HeldenPlugin.getInstance().getVillagerManager().getCount(player.getUniqueId());
        for (int i = 0; i < player.getInventory().getSize(); ++i) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack == null || itemStack.getType() != Material.OAK_SIGN) {
                continue;
            }
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta == null || !"\u00a76\u00a7lVillager-\u00dcbersicht".equals(itemMeta.getDisplayName())) {
                continue;
            }
            itemMeta.setLore(Arrays.asList("\u00a77Deine Villager: \u00a7a" + count, "\u00a77Einnahmen alle 4 Tage: \u00a7a" + count * 5 + " Emeralds", "\u00a77\u00a7o/villagerinfo f\u00fcr Details"));
            itemStack.setItemMeta(itemMeta);
        }
    }
}
