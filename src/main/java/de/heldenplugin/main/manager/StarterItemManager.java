package de.heldenplugin.main.manager;

import de.heldenplugin.main.HeldenPlugin;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StarterItemManager {
    private final HeldenPlugin plugin;
    private List<ItemStack> items = new ArrayList<ItemStack>();

    public StarterItemManager(HeldenPlugin heldenMod) {
        this.plugin = heldenMod;
        this.load();
    }

    private void load() {
        this.items.clear();
        List list = this.plugin.getConfig().getList("starter-items");
        if (list != null) {
            for (Object e : list) {
                if (!(e instanceof ItemStack)) continue;
                this.items.add((ItemStack)e);
            }
        }
    }

    public void setStarterItems(List<ItemStack> list) {
        this.items = new ArrayList<ItemStack>(list);
        this.plugin.getConfig().set("starter-items", this.items);
        this.plugin.saveConfig();
    }

    public List<ItemStack> getStarterItems() {
        return new ArrayList<ItemStack>(this.items);
    }

    public void giveStarterItems(Player player) {
        player.getInventory().addItem(new ItemStack[]{new ItemStack(Material.EMERALD, 20)});
        for (ItemStack itemStack : this.items) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            player.getInventory().addItem(new ItemStack[]{itemStack.clone()});
        }
    }
}

