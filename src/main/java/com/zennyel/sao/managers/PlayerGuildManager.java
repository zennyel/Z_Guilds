package com.zennyel.sao.managers;

import com.zennyel.sao.Z_Guilds;
import com.zennyel.sao.database.MainDB;
import com.zennyel.sao.objects.guild.Guild;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerGuildManager {

    private MainDB db;
    private GuildManager guildManager;
    private HashMap<Player, UUID> playerGuildHashmap;
    private Z_Guilds instance;

    public PlayerGuildManager(MainDB db, GuildManager guildManager, Z_Guilds instance) {
        this.instance = instance;
        this.playerGuildHashmap = new HashMap<>();
        this.db = db;
        this.guildManager = guildManager;
    }

    public boolean hasGuild(Player player){
        return playerGuildHashmap.get(player) != null;
    }


    public void loadPlayerGuild(Player player){
        Bukkit.getScheduler().runTaskAsynchronously(instance, ()->{
            if(db.getPlayerGuild(player.getUniqueId().toString()) == null){
            return;
        }
        UUID guild_id = UUID.fromString(db.getPlayerGuild(player.getUniqueId().toString()));
        playerGuildHashmap.put(player, guild_id);
        });
    }

    public boolean savePlayerGuild(Player player){
            Bukkit.getScheduler().runTaskAsynchronously(instance, ()->{
            if(db.getPlayerGuild(player.getUniqueId().toString()) != null){
            return;
        }
        UUID guild_id = playerGuildHashmap.get(player);
        db.insertPlayerGuild(player.getUniqueId().toString(), guild_id.toString());
            });
            return true;
    }

    public void createGuild(String name, Player owner) {
        Guild guild = new Guild(name, owner);
        guildManager.saveGuild(guild.getGuildId());
        setPlayerGuild(owner, guild);
    }

    public void setPlayerGuild(Player player, Guild guild){
        playerGuildHashmap.put(player, guild.getGuildId());
    }

    public UUID getPlayerGuildUUID(Player player){
        return playerGuildHashmap.get(player);
    }

    public Guild getPlayerGuild(Player player){
      return guildManager.getGuild(getPlayerGuildUUID(player));
    }


}
