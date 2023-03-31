package com.zennyel.sao.config;

import com.zennyel.sao.Z_Guilds;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public abstract class CustomConfiguration {

    private File configFile;
    private FileConfiguration configuration;
    private Reader stream;
    private YamlConfiguration defaultConfig;
    private Z_Guilds instance;
    private String fileName;

    public CustomConfiguration(Z_Guilds instance, String fileName) {
        this.fileName =  fileName;
        this.instance = instance;
    }
    public String getMessage(String path, Player player) {
        String message = getConfiguration().getString(path);
        return message.replace("&", "§").replace("{player}", player.getName());
    }

    public String getMessage(String path){
        String message = getConfiguration().getString(path);
        if(message == null){
            return null;
        }
        return message.replace("&", "§").
                replace("{tag}", getConfiguration().getString("tag-placeholder").
                        replace("&", "§"));
    }

    public Z_Guilds getInstance() {
        return instance;
    }

    public YamlConfiguration getDefaultConfig() {
        return defaultConfig;
    }

    public FileConfiguration getConfiguration(){
        this.configFile = new File(getInstance().getDataFolder(), fileName + ".yml");
        if (!configFile.exists()) {
            try {
                this.configuration = YamlConfiguration.loadConfiguration(configFile);
                this.defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(getInstance().getResource(fileName + ".yml"), StandardCharsets.UTF_8));
                configuration.setDefaults(defaultConfig);
                getInstance().saveResource(fileName + ".yml", false);
                return configuration;
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
        this.configuration = YamlConfiguration.loadConfiguration(configFile);
        this.defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(getInstance().getResource(fileName + ".yml"), StandardCharsets.UTF_8));
        configuration.setDefaults(defaultConfig);
        return configuration;
    }


}
