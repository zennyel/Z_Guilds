package com.zennyel.sao.threads;

import com.zennyel.sao.Z_Guilds;
import com.zennyel.sao.managers.PluginManager;

public class PluginManagerLoader extends Thread {
    private Z_Guilds instance;
    private PluginManager pluginManager;

    public PluginManagerLoader(Z_Guilds instance) {
        this.instance = instance;
    }

    @Override
    public void run() {
        pluginManager = new PluginManager(instance);
        pluginManager.instantiateObjects();
        pluginManager.loadPlayerData();
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }
}
