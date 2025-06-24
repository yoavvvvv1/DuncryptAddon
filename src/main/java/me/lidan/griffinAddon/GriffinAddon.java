package me.lidan.griffinAddon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class GriffinAddon extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerEvent(this);
        getLogger().info("GriffinAddon has been enabled!");
    }

    /**
     * Register event
     *
     * @param listener the listener to register
     */
    public void registerEvent(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        getLogger().info("Block broken: " + event.getBlock().getType() + " by " + event.getPlayer().getName());
    }

}
