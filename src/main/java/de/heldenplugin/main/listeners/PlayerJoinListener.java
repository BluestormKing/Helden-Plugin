package de.heldenplugin.main.listeners;

import de.heldenplugin.main.HeldenPlugin;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener
implements Listener {
    private final HeldenPlugin plugin;

    public PlayerJoinListener(HeldenPlugin heldenMod) {
        this.plugin = heldenMod;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        String string;
        Player player = playerJoinEvent.getPlayer();
        List list = this.plugin.getConfig().getStringList("first-join-players");
        if (!list.contains(string = player.getUniqueId().toString())) {
            list.add(string);
            this.plugin.getConfig().set("first-join-players", (Object)list);
            this.plugin.saveConfig();
            this.plugin.getStarterItemManager().giveStarterItems(player);
            player.sendMessage("\u00a7a\u00a7l=== Willkommen! ===");
            player.sendMessage("\u00a77Du hast \u00a7a20 Emeralds \u00a77+ Starter-Items erhalten!");
            player.sendMessage("\u00a77Tippe \u00a7e/setbase \u00a77um deine Basis zu setzen.");
            player.sendMessage("\u00a77Shop: Rechtsklicke den Notenblock beim Shop-PC.");
        }
    }
}

