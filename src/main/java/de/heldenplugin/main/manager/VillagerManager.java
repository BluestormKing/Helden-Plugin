package de.heldenplugin.main.manager;

import de.heldenplugin.main.HeldenPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VillagerManager {
    private final HeldenPlugin plugin;
    private final Map<UUID, List<UUID>> owners = new HashMap<UUID, List<UUID>>();

    public VillagerManager(HeldenPlugin heldenMod) {
        this.plugin = heldenMod;
        this.load();
    }

    private void load() {
        this.owners.clear();
        ConfigurationSection configurationSection = this.plugin.getConfig().getConfigurationSection("villager-owners");
        if (configurationSection == null) {
            return;
        }
        for (String string : configurationSection.getKeys(false)) {
            try {
                UUID uUID = UUID.fromString(string);
                List<String> list = configurationSection.getStringList(string);
                ArrayList<UUID> arrayList = new ArrayList<UUID>();
                for (String string2 : list) {
                    try {
                        arrayList.add(UUID.fromString(string2));
                    }
                    catch (Exception exception) {}
                }
                this.owners.put(uUID, arrayList);
            }
            catch (Exception exception) {}
        }
    }

    public void save() {
        for (Map.Entry<UUID, List<UUID>> entry : this.owners.entrySet()) {
            ArrayList<String> arrayList = new ArrayList<String>();
            for (UUID uUID : entry.getValue()) {
                arrayList.add(uUID.toString());
            }
            this.plugin.getConfig().set("villager-owners." + String.valueOf(entry.getKey()), arrayList);
        }
        this.plugin.saveConfig();
    }

    public void register(UUID uUID2, UUID uUID3) {
        this.owners.computeIfAbsent(uUID2, uUID -> new ArrayList()).add(uUID3);
        this.save();
    }

    public void unregister(UUID villagerUuid) {
        for (List<UUID> list : this.owners.values()) {
            if (list.remove(villagerUuid)) {
                this.save();
                return;
            }
        }
    }

    public int getCount(UUID uUID) {
        List<UUID> list = this.owners.get(uUID);
        return list == null ? 0 : list.size();
    }

    public UUID getOwner(UUID uUID) {
        for (Map.Entry<UUID, List<UUID>> entry : this.owners.entrySet()) {
            if (!entry.getValue().contains(uUID)) continue;
            return entry.getKey();
        }
        return null;
    }

    public void payAllOwners() {
        for (Map.Entry<UUID, List<UUID>> entry : this.owners.entrySet()) {
            Player player;
            if (entry.getValue().isEmpty() || (player = Bukkit.getPlayer((UUID)entry.getKey())) == null || !player.isOnline()) continue;
            int n = entry.getValue().size() * 5;
            player.getInventory().addItem(new ItemStack[]{new ItemStack(Material.EMERALD, n)});
            player.sendMessage("\u00a7a\u00a7lVillager-Einkommen: \u00a7f+" + n + " Emeralds \u00a77(" + entry.getValue().size() + " x 5)");
        }
    }
}

