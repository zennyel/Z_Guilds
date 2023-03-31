package com.zennyel.sao;

import com.zennyel.sao.commands.GuildCommand;
import com.zennyel.sao.commands.ReloadCommand;
import com.zennyel.sao.liesteners.PlayerInviteListener;
import com.zennyel.sao.managers.PluginManager;
import com.zennyel.sao.threads.PluginManagerLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Z_Guilds extends JavaPlugin {

    private PluginManager manager;

    @Override
    public void onLoad(){
        PluginManagerLoader loader = new PluginManagerLoader(this);
        loader.start();
        try {
            loader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        manager = loader.getPluginManager();
    }


    @Override
    public void onEnable() {
        manager.setupSql();
        registerEvents();
        registerCommands();
        saveDefaultConfig();
        startMessage();

    }
    public void startMessage(){
        List<String> messages = new ArrayList<>();
        ChatColor color = ChatColor.DARK_GREEN;
        messages.add("§6___   §5____");
        messages.add("§6  /  §5|  __   §6ZGuilds §5v1.2");
        messages.add("§6 /__ §5|___|   §2Link §2Start!");
        messages.add(" ");
        for(String s : messages){
            Bukkit.getConsoleSender().sendMessage(s);
        }
    }

    public void registerEvents(){
        org.bukkit.plugin.PluginManager pm = Bukkit.getPluginManager();;
        pm.registerEvents(new PlayerInviteListener(manager.getMessages()), this);
    }

    public void registerCommands(){
        getCommand("guilda").setExecutor(new GuildCommand(this));
        getCommand("reload").setExecutor(new ReloadCommand(this, manager.getMessages()));
    }

    public void setManager(PluginManager manager) {
        this.manager = manager;
    }

    public PluginManager getManager() {
        return manager;
    }
}
