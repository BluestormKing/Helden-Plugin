package de.heldenplugin.main.manager;

import de.heldenplugin.main.HeldenPlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class BaseManager {
    private final HeldenPlugin plugin;
    private final Map<UUID, Location> bases = new HashMap<UUID, Location>();

    public BaseManager(HeldenPlugin heldenMod) {
        this.plugin = heldenMod;
        this.load();
    }

    private void load() {
        this.bases.clear();
        ConfigurationSection configurationSection = this.plugin.getConfig().getConfigurationSection("bases");
        if (configurationSection == null) {
            return;
        }
        for (String string : configurationSection.getKeys(false)) {
            try {
                UUID uUID = UUID.fromString(string);
                Location location = (Location)configurationSection.get(string);
                if (location == null) continue;
                this.bases.put(uUID, location);
            }
            catch (Exception exception) {}
        }
    }

    public void setBase(Player player) {
        this.bases.put(player.getUniqueId(), player.getLocation().clone());
        for (Map.Entry<UUID, Location> entry : this.bases.entrySet()) {
            this.plugin.getConfig().set("bases." + String.valueOf(entry.getKey()), (Object)entry.getValue());
        }
        this.plugin.saveConfig();
    }

    public boolean hasBase(UUID uUID) {
        return this.bases.containsKey(uUID);
    }

    public Location getBase(UUID uUID) {
        return this.bases.get(uUID);
    }

    public boolean isOwnBase(Location location, UUID uUID) {
        Location location2 = this.bases.get(uUID);
        if (location2 == null || !location2.getWorld().equals((Object)location.getWorld())) {
            return false;
        }
        return location2.distance(location) <= 30.0;
    }

    public boolean isInForeignBase(Location location, UUID uUID) {
        for (Map.Entry<UUID, Location> entry : this.bases.entrySet()) {
            Location location2;
            if (entry.getKey().equals(uUID) || !(location2 = entry.getValue()).getWorld().equals((Object)location.getWorld()) || !(location2.distance(location) <= 30.0)) continue;
            return true;
        }
        return false;
    }

    public Map<UUID, Location> getAllBases() {
        return this.bases;
    }
}

