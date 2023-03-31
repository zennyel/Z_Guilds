package com.zennyel.sao.managers;

import com.zennyel.sao.Z_Guilds;
import com.zennyel.sao.database.MainDB;
import com.zennyel.sao.objects.guild.Guild;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class GuildManager{

    private MainDB guildDB;
    private HashMap<UUID, Guild >guildHashMap;
    private Z_Guilds instance;


    public GuildManager(MainDB guildDB, Z_Guilds instance) {
        this.guildHashMap = new HashMap<>();
        this.guildDB = guildDB;
        this.instance = instance;
    }

    public void loadGuild(UUID key) {
        Bukkit.getScheduler().runTaskAsynchronously(instance, ()->{
            if(guildHashMap.get(key) == null){
            if(guildDB.getGuild(key) != null) {
                guildHashMap.put(key, guildDB.getGuild(key));
            }
        }
        });
    }


    public void saveGuild(UUID key) {
        Bukkit.getScheduler().runTaskAsynchronously(instance, ()->{
            Guild guild = guildHashMap.get(key);
        if (guild != null) {
            guildDB.insertGuild(guild.getName(), guild.getGuildId(), guild.getHome());
            guildDB.insertGuildMembers(key, guild.getMembers());
            guildDB.insertGuildAllies(key, guild.getAllies());
            guildDB.insertGuildEnemies(key, guild.getEnemies());
        }
        });
    }

    public Z_Guilds getInstance() {
        return instance;
    }

    public Guild getGuild(UUID key) {
        return guildHashMap.get(key);
    }
}

