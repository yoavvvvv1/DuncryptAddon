package me.lidan.griffinAddon.griffin;


import me.lidan.cavecrawlers.objects.ConfigLoader;
import me.lidan.griffinAddon.GriffinAddon;

import java.io.File;

public class GriffinLoader extends ConfigLoader<GriffinDrops> {
    private static GriffinLoader instance;
    private final GriffinManager griffinManager;

    private GriffinLoader() {
        super(GriffinDrops.class, new File(GriffinAddon.getInstance().getDataFolder(), "griffin"));
        griffinManager = GriffinManager.getInstance();
    }

    @Override
    public void register(String key, GriffinDrops value) {
        griffinManager.registerDrop(key, value);
    }

    public static GriffinLoader getInstance() {
        if (instance == null) {
            instance = new GriffinLoader();
        }
        return instance;
    }
}
