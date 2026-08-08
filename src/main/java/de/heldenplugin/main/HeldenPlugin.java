package de.heldenplugin.main;

import de.heldenplugin.main.commands.PlaceShopCommand;
import de.heldenplugin.main.commands.SetBaseCommand;
import de.heldenplugin.main.commands.SetStartItemsCommand;
import de.heldenplugin.main.commands.VillagerInfoCommand;
import de.heldenplugin.main.listeners.BlockProtectionListener;
import de.heldenplugin.main.listeners.PlayerJoinListener;
import de.heldenplugin.main.listeners.ShopListener;
import de.heldenplugin.main.listeners.StarterItemSetupListener;
import de.heldenplugin.main.listeners.VillagerListener;
import de.heldenplugin.main.manager.BaseManager;
import de.heldenplugin.main.manager.ShopManager;
import de.heldenplugin.main.manager.StarterItemManager;
import de.heldenplugin.main.manager.VillagerManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class HeldenPlugin
extends JavaPlugin {
    private static HeldenPlugin instance;
    private BaseManager baseManager;
    private VillagerManager villagerManager;
    private ShopManager shopManager;
    private StarterItemManager starterItemManager;

    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.baseManager = new BaseManager(this);
        this.villagerManager = new VillagerManager(this);
        this.shopManager = new ShopManager(this);
        this.starterItemManager = new StarterItemManager(this);
        this.getCommand("setstartitems").setExecutor((CommandExecutor)new SetStartItemsCommand(this));
        this.getCommand("placeshop").setExecutor((CommandExecutor)new PlaceShopCommand(this));
        this.getCommand("setbase").setExecutor((CommandExecutor)new SetBaseCommand(this));
        this.getCommand("villagerinfo").setExecutor((CommandExecutor)new VillagerInfoCommand(this));
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerJoinListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new BlockProtectionListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new ShopListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new VillagerListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new StarterItemSetupListener(this), (Plugin)this);
        this.getServer().getScheduler().runTaskTimer((Plugin)this, new Runnable(){

            @Override
            public void run() {
                HeldenPlugin.this.villagerManager.payAllOwners();
            }
        }, 96000L, 96000L);
        this.getLogger().info("HeldenPlugin v1.0 geladen!");
    }

    public void onDisable() {
        this.saveConfig();
    }

    public static HeldenPlugin getInstance() {
        return instance;
    }

    public BaseManager getBaseManager() {
        return this.baseManager;
    }

    public VillagerManager getVillagerManager() {
        return this.villagerManager;
    }

    public ShopManager getShopManager() {
        return this.shopManager;
    }

    public StarterItemManager getStarterItemManager() {
        return this.starterItemManager;
    }
}

