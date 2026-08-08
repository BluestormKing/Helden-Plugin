package de.heldenplugin.main.commands;

import de.heldenplugin.main.HeldenPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaceShopCommand
implements CommandExecutor {
    private final HeldenPlugin plugin;

    public PlaceShopCommand(HeldenPlugin heldenMod) {
        this.plugin = heldenMod;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] stringArray) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("\u00a7cNur Spieler!");
            return true;
        }
        Player player = (Player)commandSender;
        if (!player.hasPermission("helden-plugin.admin")) {
            player.sendMessage("\u00a7cKeine Berechtigung!");
            return true;
        }
        World world = player.getWorld();
        Location playerLocation = player.getLocation();
        int x = (int)Math.floor(playerLocation.getX());
        int y = (int)Math.floor(playerLocation.getY());
        int z = (int)Math.floor(playerLocation.getZ());
        Location location = new Location(world, (double)x, (double)y, (double)z);
        location.getBlock().setType(Material.NOTE_BLOCK);
        Block block = location.clone().add(0.0, 1.0, 0.0).getBlock();
        block.setType(Material.OAK_WALL_SIGN);
        if (block.getState() instanceof Sign) {
            Sign sign = (Sign)block.getState();
            sign.setLine(0, "\u00a78================");
            sign.setLine(1, "\u00a7a\u00a7lSHOP");
            sign.setLine(2, "\u00a77Rechtsklick \u00f6ffnen");
            sign.setLine(3, "\u00a78================");
            sign.update();
        }
        this.plugin.getShopManager().setShopLocation(location);
        player.sendMessage("\u00a7a[Shop] \u00a7fShop-PC bei \u00a7e" + x + ", " + y + ", " + z + " \u00a7fgesetzt!");
        return true;
    }
}

