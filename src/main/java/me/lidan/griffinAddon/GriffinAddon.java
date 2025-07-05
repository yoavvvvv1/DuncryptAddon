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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class GriffinAddon extends JavaPlugin implements Listener {

    public static final int BUFFER_SIZE = 1024;

    public static GriffinAddon getInstance() {
        return GriffinAddon.getPlugin(GriffinAddon.class);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultResources();
        registerSerializers();
        registerAbilities();
        registerGriffin();
        registerCommands();
        registerEvents();
        getLogger().info("GriffinAddon has been enabled!");
    }

    /**
     * Save default resources
     */
    private void saveDefaultResources() {
        if (getDataFolder().exists()) {
            return;
        }
        getLogger().info("Detected first time setup, saving default resources");
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        getDataFolder().mkdir();
        saveResource("COMMON.yml", new File(getDataFolder(), "griffin/COMMON.yml"));
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

    /**
     * Save a resource to a file path
     * Used to save resources to subdirectories in the plugin folder
     *
     * @param resource the resource
     * @param path     the path as a File object
     */
    public void saveResource(String resource, File path) {
        if (!path.exists()) {
            path.getParentFile().mkdirs();
            try (InputStream in = getResource(resource);
                 FileOutputStream out = new FileOutputStream(path)) {
                if (in == null) {
                    getLogger().warning("Resource not found: " + resource);
                    return;
                }
                byte[] buffer = new byte[BUFFER_SIZE];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
