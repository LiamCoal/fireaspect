package com.liamcoalstudios.liamplugin.fireaspect;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class FireAspect extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Material output = Material.IRON_INGOT;
        Random rand = new Random();
        int fortune = event.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        int count = 1 + rand.nextInt(fortune);
        boolean canMineGold = true;
        switch (event.getPlayer().getInventory().getItemInMainHand().getType()) {
            case STONE_PICKAXE:
                canMineGold = false;
            case IRON_PICKAXE:
            case DIAMOND_PICKAXE:
            case NETHERITE_PICKAXE:
                switch (event.getBlock().getType()) {
                    case GOLD_ORE:
                        if(!canMineGold) break;
                        output = Material.GOLD_INGOT;
                    case IRON_ORE:
                        event.setDropItems(false);
                        event.getBlock().getWorld().dropItemNaturally(
                                event.getBlock().getLocation(),
                                new ItemStack(output, count)
                        );
                        for (int i = 0; i < rand.nextInt(5) + fortune; i++) {
                            event.getBlock().getWorld().spawnEntity(
                                    event.getBlock().getLocation().add(rand.nextFloat() % 0.5f, rand.nextFloat() % 0.5f, rand.nextFloat() % 0.5f),
                                    EntityType.EXPERIENCE_ORB
                            );
                        }
                        event.getBlock().getWorld().spawnParticle(
                                Particle.LAVA,
                                event.getBlock().getLocation(),
                                5,
                                0.5,
                                0.5,
                                0.5
                        );
                        break;
                }
                break;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
