package me.lidan.griffinAddon.griffin;

import me.lidan.cavecrawlers.griffin.GriffinDrops;
import me.lidan.cavecrawlers.griffin.GriffinManager;
import me.lidan.cavecrawlers.objects.ConfigLoader;

public class GriffinLoader extends ConfigLoader<me.lidan.cavecrawlers.griffin.GriffinDrops> {
    private static GriffinLoader instance;
    private final GriffinManager griffinManager;

    private GriffinLoader() {
        super(me.lidan.cavecrawlers.griffin.GriffinDrops.class, "griffin");
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
