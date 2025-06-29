package me.lidan.griffinAddon;

import me.lidan.cavecrawlers.CaveCrawlers;
import me.lidan.griffinAddon.abilities.SpadeAbility;
import me.lidan.griffinAddon.griffin.GriffinCommand;
import me.lidan.griffinAddon.griffin.GriffinDrop;
import me.lidan.griffinAddon.griffin.GriffinDrops;
import me.lidan.griffinAddon.griffin.GriffinLoader;
import me.lidan.griffinAddon.listeners.GriffinListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public final class GriffinAddon extends JavaPlugin implements Listener {

    public static GriffinAddon getInstance() {
        return GriffinAddon.getPlugin(GriffinAddon.class);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        registerSerializers();
        registerAbilities();
        registerGriffin();
        registerCommands();
        registerEvents();
        getLogger().info("GriffinAddon has been enabled!");
    }

    private void registerAbilities() {
        CaveCrawlers.getAPI().getAbilityAPI().registerAbility("SPADE", new SpadeAbility());
    }

    private void registerEvents() {
        registerEvent(this);
        registerEvent(new GriffinListener());
    }

    private void registerCommands() {
        BukkitCommandHandler commandHandler = BukkitCommandHandler.create(this);
        commandHandler.register(new GriffinCommand());
        commandHandler.registerBrigadier();
    }

    private void registerSerializers() {
        ConfigurationSerialization.registerClass(GriffinDrop.class);
        ConfigurationSerialization.registerClass(GriffinDrops.class);
    }

    /**
     * Register event
     *
     * @param listener the listener to register
     */
    public void registerEvent(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    /**
     * Register griffin
     */
    private void registerGriffin() {
        GriffinLoader.getInstance().load();
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        getLogger().info("Block broken: " + event.getBlock().getType() + " by " + event.getPlayer().getName());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getScheduler().cancelTasks(this);
    }

}
