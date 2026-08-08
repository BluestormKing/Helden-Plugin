package de.heldenplugin.main.commands;

import de.heldenplugin.main.HeldenPlugin;
import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetBaseCommand
implements CommandExecutor {
    private final HeldenPlugin plugin;

    public SetBaseCommand(HeldenPlugin heldenMod) {
        this.plugin = heldenMod;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] stringArray) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("\u00a7cNur Spieler!");
            return true;
        }
        Player player = (Player)commandSender;
        if (this.plugin.getBaseManager().hasBase(player.getUniqueId())) {
            player.sendMessage("\u00a7c[Basis] \u00a7fDu hast bereits eine Basis!");
            return true;
        }
        this.plugin.getBaseManager().setBase(player);
        ItemStack itemStack = new ItemStack(Material.OAK_SIGN);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName("\u00a76\u00a7lVillager-\u00dcbersicht");
            itemMeta.setLore(Arrays.asList("\u00a77Deine Villager: \u00a7a0", "\u00a77Einnahmen alle 4 Tage: \u00a7a0 Emeralds", "\u00a77/villagerinfo f\u00fcr Details"));
            itemStack.setItemMeta(itemMeta);
        }
        player.getInventory().addItem(new ItemStack[]{itemStack});
        player.sendMessage("\u00a7a[Basis] \u00a7fGesetzt bei \u00a7e" + (int)player.getLocation().getX() + ", " + (int)player.getLocation().getY() + ", " + (int)player.getLocation().getZ() + "\u00a7f!");
        player.sendMessage("\u00a7730-Block-Radius ist gesch\u00fctzt.");
        return true;
    }
}

