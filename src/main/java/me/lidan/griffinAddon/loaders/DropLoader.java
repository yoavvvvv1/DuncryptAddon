package me.lidan.griffinAddon.loaders;

import me.lidan.cavecrawlers.drops.DropsManager;
import me.lidan.cavecrawlers.drops.EntityDrops;
import me.lidan.cavecrawlers.objects.ConfigLoader;
import me.lidan.griffinAddon.GriffinAddon;

import java.io.File;

public class DropLoader extends ConfigLoader<EntityDrops> {

    private static DropLoader instance;
    private final DropsManager dropsManager = DropsManager.getInstance();

    private DropLoader() {
        super(EntityDrops.class, new File(GriffinAddon.getInstance().getDataFolder(), "drops"));
    }

    public static DropLoader getInstance() {
        if (instance == null) {
            instance = new DropLoader();
        }
        return instance;
    }

    @Override
    public void register(String key, EntityDrops value) {
        dropsManager.register(value.getEntityName(), value);
    }

    @Override
    public void clear() {
        super.clear();
        dropsManager.clear();
    }
}
