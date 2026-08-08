package de.heldenplugin.main.manager;

import de.heldenplugin.main.HeldenPlugin;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.Material;

public class ShopManager {
    private final HeldenPlugin plugin;
    private Location shopLoc;
    private final Map<Material, Double> prices = new LinkedHashMap<Material, Double>();

    public ShopManager(HeldenPlugin heldenMod) {
        this.plugin = heldenMod;
        this.buildPrices();
        this.loadLoc();
    }

    private void buildPrices() {
        for (Material material : new Material[]{Material.STONE, Material.COBBLESTONE, Material.STONE_BRICKS, Material.MOSSY_COBBLESTONE, Material.MOSSY_STONE_BRICKS, Material.CRACKED_STONE_BRICKS, Material.CHISELED_STONE_BRICKS, Material.SMOOTH_STONE, Material.GRANITE, Material.POLISHED_GRANITE, Material.DIORITE, Material.POLISHED_DIORITE, Material.ANDESITE, Material.POLISHED_ANDESITE, Material.DEEPSLATE, Material.COBBLED_DEEPSLATE, Material.POLISHED_DEEPSLATE, Material.DEEPSLATE_BRICKS, Material.DEEPSLATE_TILES, Material.CRACKED_DEEPSLATE_BRICKS, Material.CRACKED_DEEPSLATE_TILES, Material.CHISELED_DEEPSLATE, Material.TUFF, Material.CALCITE, Material.DRIPSTONE_BLOCK, Material.SANDSTONE, Material.SMOOTH_SANDSTONE, Material.CHISELED_SANDSTONE, Material.CUT_SANDSTONE, Material.RED_SANDSTONE, Material.SMOOTH_RED_SANDSTONE, Material.CHISELED_RED_SANDSTONE, Material.CUT_RED_SANDSTONE, Material.PRISMARINE, Material.PRISMARINE_BRICKS, Material.DARK_PRISMARINE, Material.BASALT, Material.POLISHED_BASALT, Material.SMOOTH_BASALT, Material.BLACKSTONE, Material.POLISHED_BLACKSTONE, Material.POLISHED_BLACKSTONE_BRICKS, Material.CRACKED_POLISHED_BLACKSTONE_BRICKS, Material.CHISELED_POLISHED_BLACKSTONE, Material.END_STONE, Material.END_STONE_BRICKS, Material.PURPUR_BLOCK, Material.PURPUR_PILLAR, Material.GRAVEL, Material.CLAY, Material.DIRT, Material.COARSE_DIRT, Material.PODZOL, Material.ROOTED_DIRT, Material.MUD, Material.PACKED_MUD, Material.MUD_BRICKS, Material.GRASS_BLOCK, Material.MYCELIUM, Material.SAND, Material.RED_SAND, Material.TERRACOTTA, Material.WHITE_TERRACOTTA, Material.ORANGE_TERRACOTTA, Material.MAGENTA_TERRACOTTA, Material.LIGHT_BLUE_TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.LIME_TERRACOTTA, Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA, Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA, Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA, Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA, Material.BLACK_TERRACOTTA, Material.WHITE_CONCRETE, Material.ORANGE_CONCRETE, Material.MAGENTA_CONCRETE, Material.LIGHT_BLUE_CONCRETE, Material.YELLOW_CONCRETE, Material.LIME_CONCRETE, Material.PINK_CONCRETE, Material.GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE, Material.CYAN_CONCRETE, Material.PURPLE_CONCRETE, Material.BLUE_CONCRETE, Material.BROWN_CONCRETE, Material.GREEN_CONCRETE, Material.RED_CONCRETE, Material.BLACK_CONCRETE, Material.OBSIDIAN, Material.CRYING_OBSIDIAN, Material.ICE, Material.PACKED_ICE, Material.BLUE_ICE, Material.SNOW_BLOCK, Material.BRICKS, Material.NETHER_BRICKS, Material.RED_NETHER_BRICKS, Material.CRACKED_NETHER_BRICKS, Material.CHISELED_NETHER_BRICKS, Material.QUARTZ_BLOCK, Material.SMOOTH_QUARTZ, Material.QUARTZ_PILLAR, Material.CHISELED_QUARTZ_BLOCK, Material.QUARTZ_BRICKS}) {
            this.prices.put(material, 10.0);
        }
        for (Material material : new Material[]{Material.OAK_LOG, Material.OAK_WOOD, Material.STRIPPED_OAK_LOG, Material.STRIPPED_OAK_WOOD, Material.OAK_PLANKS, Material.OAK_SLAB, Material.OAK_STAIRS, Material.OAK_FENCE, Material.SPRUCE_LOG, Material.SPRUCE_WOOD, Material.STRIPPED_SPRUCE_LOG, Material.STRIPPED_SPRUCE_WOOD, Material.SPRUCE_PLANKS, Material.SPRUCE_SLAB, Material.SPRUCE_STAIRS, Material.SPRUCE_FENCE, Material.BIRCH_LOG, Material.BIRCH_WOOD, Material.STRIPPED_BIRCH_LOG, Material.STRIPPED_BIRCH_WOOD, Material.BIRCH_PLANKS, Material.BIRCH_SLAB, Material.BIRCH_STAIRS, Material.BIRCH_FENCE, Material.JUNGLE_LOG, Material.JUNGLE_WOOD, Material.STRIPPED_JUNGLE_LOG, Material.STRIPPED_JUNGLE_WOOD, Material.JUNGLE_PLANKS, Material.JUNGLE_SLAB, Material.JUNGLE_STAIRS, Material.JUNGLE_FENCE, Material.ACACIA_LOG, Material.ACACIA_WOOD, Material.STRIPPED_ACACIA_LOG, Material.STRIPPED_ACACIA_WOOD, Material.ACACIA_PLANKS, Material.ACACIA_SLAB, Material.ACACIA_STAIRS, Material.ACACIA_FENCE, Material.DARK_OAK_LOG, Material.DARK_OAK_WOOD, Material.STRIPPED_DARK_OAK_LOG, Material.STRIPPED_DARK_OAK_WOOD, Material.DARK_OAK_PLANKS, Material.DARK_OAK_SLAB, Material.DARK_OAK_STAIRS, Material.DARK_OAK_FENCE, Material.MANGROVE_LOG, Material.MANGROVE_WOOD, Material.STRIPPED_MANGROVE_LOG, Material.STRIPPED_MANGROVE_WOOD, Material.MANGROVE_PLANKS, Material.MANGROVE_SLAB, Material.MANGROVE_STAIRS, Material.MANGROVE_ROOTS, Material.CHERRY_LOG, Material.CHERRY_WOOD, Material.STRIPPED_CHERRY_LOG, Material.STRIPPED_CHERRY_WOOD, Material.CHERRY_PLANKS, Material.CHERRY_SLAB, Material.CHERRY_STAIRS}) {
            this.prices.put(material, 5.0);
        }
        this.prices.put(Material.BREAD, 3.0);
        this.prices.put(Material.COOKED_BEEF, 3.0);
        this.prices.put(Material.SWEET_BERRIES, 10.0);
        this.prices.put(Material.IRON_INGOT, 0.5);
        this.prices.put(Material.COW_SPAWN_EGG, 5.0);
        this.prices.put(Material.SHEEP_SPAWN_EGG, 5.0);
        this.prices.put(Material.CHICKEN_SPAWN_EGG, 5.0);
    }

    public void loadLoc() {
        if (this.plugin.getConfig().contains("shop-location")) {
            this.shopLoc = (Location)this.plugin.getConfig().get("shop-location");
        }
    }

    public void setShopLocation(Location location) {
        this.shopLoc = location;
        this.plugin.getConfig().set("shop-location", (Object)location);
        this.plugin.saveConfig();
    }

    public Location getShopLocation() {
        return this.shopLoc;
    }

    public Map<Material, Double> getPrices() {
        return this.prices;
    }

    public boolean isSpawnEgg(Material material) {
        return material == Material.COW_SPAWN_EGG || material == Material.SHEEP_SPAWN_EGG || material == Material.CHICKEN_SPAWN_EGG;
    }

    public double calcPrice(Material material, int n) {
        if (material == Material.IRON_INGOT) {
            return Math.ceil((double)n / 2.0);
        }
        Double d = this.prices.get(material);
        if (d == null) {
            return -1.0;
        }
        if (this.isSpawnEgg(material)) {
            return d * (double)n;
        }
        return Math.ceil((double)n / 64.0 * d);
    }

    public static boolean isForbidden(Material material) {
        if (material == Material.EMERALD || material == Material.EMERALD_BLOCK) {
            return true;
        }
        if (material == Material.WRITTEN_BOOK || material == Material.WRITABLE_BOOK) {
            return true;
        }
        String string = material.name();
        if (string.endsWith("_HELMET") || string.endsWith("_CHESTPLATE") || string.endsWith("_LEGGINGS") || string.endsWith("_BOOTS")) {
            return true;
        }
        return material == Material.NETHERRACK || material == Material.SOUL_SAND || material == Material.SOUL_SOIL || material == Material.NETHER_WART || material == Material.NETHER_WART_BLOCK || material == Material.WARPED_WART_BLOCK || material == Material.GLOWSTONE || material == Material.GLOWSTONE_DUST || material == Material.NETHER_QUARTZ_ORE || material == Material.NETHER_GOLD_ORE || material == Material.ANCIENT_DEBRIS || material == Material.NETHERITE_SCRAP || material == Material.NETHERITE_INGOT || material == Material.NETHERITE_BLOCK || material == Material.GILDED_BLACKSTONE || material == Material.SHROOMLIGHT || material == Material.CRIMSON_NYLIUM || material == Material.WARPED_NYLIUM || material == Material.CRIMSON_STEM || material == Material.WARPED_STEM || material == Material.CRIMSON_PLANKS || material == Material.WARPED_PLANKS || material == Material.CRIMSON_SLAB || material == Material.WARPED_SLAB || material == Material.CRIMSON_STAIRS || material == Material.WARPED_STAIRS || material == Material.CRIMSON_FENCE || material == Material.WARPED_FENCE || material == Material.HOGLIN_SPAWN_EGG || material == Material.PIGLIN_SPAWN_EGG || material == Material.STRIDER_SPAWN_EGG || material == Material.BLAZE_SPAWN_EGG || material == Material.MAGMA_CUBE_SPAWN_EGG || material == Material.GHAST_SPAWN_EGG || material == Material.ZOMBIFIED_PIGLIN_SPAWN_EGG || material == Material.WITHER_SKELETON_SPAWN_EGG || material == Material.MAGMA_BLOCK;
    }
}

