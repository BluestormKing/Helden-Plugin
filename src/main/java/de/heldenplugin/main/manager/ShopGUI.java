package de.heldenplugin.main.manager;

import de.heldenplugin.main.HeldenPlugin;
import de.heldenplugin.main.manager.ShopManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopGUI {
    private static final Map<Player, Integer> pages = new HashMap<Player, Integer>();
    private static final Map<Player, String> cats = new HashMap<Player, String>();
    public static final String MAIN_TITLE = "\u00a78\u00a7l[ \u00a7a\u00a7lSHOP \u00a78\u00a7l]";
    public static final String CAT_PREFIX = "\u00a78[ \u00a7aShop: ";

    public static void openMain(Player player) {
        Inventory inventory = Bukkit.createInventory(null, (int)27, (String)MAIN_TITLE);
        inventory.setItem(10, ShopGUI.make(Material.STONE, "\u00a77\u00a7lSteinarten", "\u00a7f64 St\u00fcck = \u00a7a10 Emeralds"));
        inventory.setItem(12, ShopGUI.make(Material.OAK_LOG, "\u00a76\u00a7lH\u00f6lzer", "\u00a7f64 St\u00fcck = \u00a7a5 Emeralds"));
        inventory.setItem(14, ShopGUI.make(Material.IRON_INGOT, "\u00a77\u00a7lEisen", "\u00a7f2 Barren = \u00a7a1 Emerald"));
        inventory.setItem(16, ShopGUI.make(Material.BREAD, "\u00a7e\u00a7lEssen", "\u00a7fBrot/Steak \u00a7a3 Em/64", "\u00a7fStachelbeeren \u00a7a10 Em/64"));
        inventory.setItem(22, ShopGUI.make(Material.COW_SPAWN_EGG, "\u00a7b\u00a7lSpawn-Eier", "\u00a7fKuh, Schaf, Huhn", "\u00a7fje \u00a7a5 Emeralds/St\u00fcck"));
        player.openInventory(inventory);
    }

    public static void openCat(Player player, String string) {
        cats.put(player, string);
        pages.put(player, 0);
        ShopGUI.openPage(player, string, 0);
    }

    public static void openPage(Player player, String string, int n) {
        int n2;
        List<Material> list = ShopGUI.getItems(string);
        int n3 = Math.max(0, (int)Math.ceil((double)list.size() / 45.0) - 1);
        n = Math.max(0, Math.min(n, n3));
        pages.put(player, n);
        Inventory inventory = Bukkit.createInventory(null, (int)54, (String)(CAT_PREFIX + ShopGUI.catName(string) + " \u00a78]"));
        ShopManager shopManager = HeldenPlugin.getInstance().getShopManager();
        for (int i = n2 = n * 45; i < Math.min(n2 + 45, list.size()); ++i) {
            Material material = list.get(i);
            inventory.setItem(i - n2, ShopGUI.make(material, "\u00a7f" + ShopGUI.fmtName(material), "\u00a77Preis: " + ShopGUI.fmtPrice(material, shopManager), "\u00a7eLinksklick: \u00a7f1x kaufen", "\u00a7eRechtsklick: \u00a7f64x kaufen"));
        }
        if (n > 0) {
            inventory.setItem(45, ShopGUI.make(Material.ARROW, "\u00a7a\u00ab Zur\u00fcck", new String[0]));
        }
        inventory.setItem(49, ShopGUI.make(Material.BARRIER, "\u00a7cHauptmen\u00fc", new String[0]));
        if (n < n3) {
            inventory.setItem(53, ShopGUI.make(Material.ARROW, "\u00a7aWeiter \u00bb", new String[0]));
        }
        player.openInventory(inventory);
    }

    private static String fmtPrice(Material material, ShopManager shopManager) {
        if (material == Material.IRON_INGOT) {
            return "\u00a7a2x = 1 Emerald";
        }
        if (shopManager.isSpawnEgg(material)) {
            return "\u00a7a5 Emeralds/St\u00fcck";
        }
        Double d = shopManager.getPrices().get(material);
        return "\u00a7a" + String.valueOf(d == null ? "?" : Integer.valueOf((int)d.doubleValue())) + " Emeralds/64";
    }

    private static List<Material> getItems(String string) {
        ArrayList<Material> arrayList = new ArrayList<Material>();
        ShopManager shopManager = HeldenPlugin.getInstance().getShopManager();
        for (Material material : shopManager.getPrices().keySet()) {
            boolean bl = shopManager.isSpawnEgg(material);
            boolean bl2 = material == Material.BREAD || material == Material.COOKED_BEEF || material == Material.SWEET_BERRIES;
            boolean bl3 = material == Material.IRON_INGOT;
            boolean bl4 = ShopGUI.isWood(material);
            switch (string) {
                case "stone": {
                    if (bl || bl2 || bl3 || bl4) break;
                    arrayList.add(material);
                    break;
                }
                case "wood": {
                    if (!bl4) break;
                    arrayList.add(material);
                    break;
                }
                case "iron": {
                    if (!bl3) break;
                    arrayList.add(material);
                    break;
                }
                case "food": {
                    if (!bl2) break;
                    arrayList.add(material);
                    break;
                }
                case "spawn": {
                    if (!bl) break;
                    arrayList.add(material);
                }
            }
        }
        return arrayList;
    }

    private static boolean isWood(Material material) {
        String string = material.name();
        if (string.startsWith("NETHER") || string.startsWith("CRIMSON") || string.startsWith("WARPED")) {
            return false;
        }
        return string.endsWith("_LOG") || string.endsWith("_WOOD") || string.endsWith("_PLANKS") || string.endsWith("_FENCE") || string.endsWith("_ROOTS") || string.equals("OAK_SLAB") || string.equals("SPRUCE_SLAB") || string.equals("BIRCH_SLAB") || string.equals("JUNGLE_SLAB") || string.equals("ACACIA_SLAB") || string.equals("DARK_OAK_SLAB") || string.equals("MANGROVE_SLAB") || string.equals("CHERRY_SLAB") || string.equals("OAK_STAIRS") || string.equals("SPRUCE_STAIRS") || string.equals("BIRCH_STAIRS") || string.equals("JUNGLE_STAIRS") || string.equals("ACACIA_STAIRS") || string.equals("DARK_OAK_STAIRS") || string.equals("MANGROVE_STAIRS") || string.equals("CHERRY_STAIRS");
    }

    private static String catName(String string) {
        switch (string) {
            case "stone": {
                return "Steinarten";
            }
            case "wood": {
                return "H\u00f6lzer";
            }
            case "iron": {
                return "Eisen";
            }
            case "food": {
                return "Essen";
            }
            case "spawn": {
                return "Spawn-Eier";
            }
        }
        return string;
    }

    public static void handleClick(Player player, Inventory inventory, String string, int n, boolean bl) {
        if (string.equals(MAIN_TITLE)) {
            switch (n) {
                case 10: {
                    ShopGUI.openCat(player, "stone");
                    break;
                }
                case 12: {
                    ShopGUI.openCat(player, "wood");
                    break;
                }
                case 14: {
                    ShopGUI.openCat(player, "iron");
                    break;
                }
                case 16: {
                    ShopGUI.openCat(player, "food");
                    break;
                }
                case 22: {
                    ShopGUI.openCat(player, "spawn");
                }
            }
            return;
        }
        if (string.startsWith(CAT_PREFIX)) {
            String string2 = cats.getOrDefault(player, "stone");
            int n2 = pages.getOrDefault(player, 0);
            if (n == 45) {
                ShopGUI.openPage(player, string2, n2 - 1);
                return;
            }
            if (n == 49) {
                ShopGUI.openMain(player);
                return;
            }
            if (n == 53) {
                ShopGUI.openPage(player, string2, n2 + 1);
                return;
            }
            if (n < 45) {
                ItemStack itemStack = inventory.getItem(n);
                if (itemStack == null || itemStack.getType() == Material.AIR) {
                    return;
                }
                ShopGUI.buy(player, itemStack.getType(), bl ? 64 : 1);
            }
        }
    }

    private static void buy(Player player, Material material, int n) {
        ShopManager shopManager = HeldenPlugin.getInstance().getShopManager();
        double d = shopManager.calcPrice(material, n);
        if (d < 0.0) {
            player.sendMessage("\u00a7cNicht kaufbar!");
            return;
        }
        int n2 = ShopGUI.countEm(player);
        if ((double)n2 < d) {
            player.sendMessage("\u00a7cNicht genug Emeralds! Brauchst \u00a7e" + (int)d + "\u00a7c, hast \u00a7e" + n2);
            return;
        }
        ShopGUI.removeEm(player, (int)d);
        player.getInventory().addItem(new ItemStack[]{new ItemStack(material, n)});
        player.sendMessage("\u00a7aGekauft: \u00a7f" + n + "x " + ShopGUI.fmtName(material) + " \u00a7af\u00fcr \u00a7e" + (int)d + " Emeralds");
    }

    private static int countEm(Player player) {
        int n = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null || itemStack.getType() != Material.EMERALD) continue;
            n += itemStack.getAmount();
        }
        return n;
    }

    private static void removeEm(Player player, int n) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null || itemStack.getType() != Material.EMERALD) continue;
            if (itemStack.getAmount() <= n) {
                n -= itemStack.getAmount();
                itemStack.setAmount(0);
                continue;
            }
            itemStack.setAmount(itemStack.getAmount() - n);
            break;
        }
    }

    public static ItemStack make(Material material, String string, String ... stringArray) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return itemStack;
        }
        itemMeta.setDisplayName(string);
        if (stringArray.length > 0) {
            itemMeta.setLore(Arrays.asList(stringArray));
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static String fmtName(Material material) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : material.name().toLowerCase().split("_")) {
            if (string.isEmpty()) continue;
            stringBuilder.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).append(" ");
        }
        return stringBuilder.toString().trim();
    }
}

