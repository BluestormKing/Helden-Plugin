package de.heldenplugin.main.commands;

import de.heldenplugin.main.HeldenPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VillagerInfoCommand
implements CommandExecutor {
    private final HeldenPlugin plugin;

    public VillagerInfoCommand(HeldenPlugin heldenMod) {
        this.plugin = heldenMod;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] stringArray) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("\u00a7cNur Spieler!");
            return true;
        }
        Player player = (Player)commandSender;
        int n = this.plugin.getVillagerManager().getCount(player.getUniqueId());
        player.sendMessage("\u00a76\u00a7l=== Villager-Info ===");
        player.sendMessage("\u00a77Deine Villager: \u00a7a" + n);
        player.sendMessage("\u00a77Einnahmen alle 4 Tage: \u00a7a" + n * 5 + " Emeralds \u00a77(5 pro Villager)");
        return true;
    }
}

