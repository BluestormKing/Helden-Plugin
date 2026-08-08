package de.heldenplugin.main.commands;

import de.heldenplugin.main.HeldenPlugin;
import de.heldenplugin.main.manager.ShopGUI;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SetStartItemsCommand
implements CommandExecutor {
    private final HeldenPlugin plugin;
    public static final String SETUP_TITLE = "\u00a78[ \u00a7eStarter-Items festlegen \u00a78]";

    public SetStartItemsCommand(HeldenPlugin heldenMod) {
        this.plugin = heldenMod;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] stringArray) {
        int n;
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("\u00a7cNur Spieler!");
            return true;
        }
        Player player = (Player)commandSender;
        if (!player.hasPermission("helden-plugin.admin")) {
            player.sendMessage("\u00a7cKeine Berechtigung!");
            return true;
        }
        Inventory inventory = Bukkit.createInventory(null, (int)54, (String)SETUP_TITLE);
        List<ItemStack> list = this.plugin.getStarterItemManager().getStarterItems();
        for (n = 0; n < Math.min(list.size(), 27); ++n) {
            inventory.setItem(n, list.get(n).clone());
        }
        for (n = 27; n < 36; ++n) {
            inventory.setItem(n, ShopGUI.make(Material.GRAY_STAINED_GLASS_PANE, "\u00a77---", new String[0]));
        }
        inventory.setItem(36, ShopGUI.make(Material.PAPER, "\u00a7e\u00a7lAnleitung", "\u00a77Items in Slots 0-26 legen", "\u00a77Neue Spieler bekommen diese Items + 20 Emeralds"));
        inventory.setItem(40, ShopGUI.make(Material.LIME_WOOL, "\u00a7a\u00a7lSpeichern & Schlie\u00dfen", "\u00a77Slots 0-26 werden gespeichert"));
        inventory.setItem(44, ShopGUI.make(Material.RED_WOOL, "\u00a7c\u00a7lAlles l\u00f6schen", new String[0]));
        player.openInventory(inventory);
        player.sendMessage("\u00a7e[Setup] \u00a7fLege Starter-Items in die oberen 27 Slots.");
        return true;
    }
}

