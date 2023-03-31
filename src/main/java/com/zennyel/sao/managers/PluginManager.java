package com.zennyel.sao.managers;

import com.zennyel.sao.Z_Guilds;
import com.zennyel.sao.config.GuildConfig;
import com.zennyel.sao.config.MessagesConfig;
import com.zennyel.sao.database.MainDB;
import com.zennyel.sao.database.type.MySQL;
import com.zennyel.sao.database.type.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PluginManager {

    private Z_Guilds instance;
    private SQLite sqLite;
    private MySQL sql;
    private MainDB playerGuildDB;
    private GuildManager guildManager;
    private PlayerGuildManager playerGuildManager;;
    private MessagesConfig messages;
    private GuildConfig guildConfig;
    private InviteManager inviteManager;

    public PluginManager(Z_Guilds instance) {
        this.instance = instance;
        instantiateObjects();
    }

    public void instantiateObjects(){
        this.sqLite = new SQLite(instance);
        this.sql = new MySQL(instance.getConfig(), sqLite);
        this.playerGuildDB = new MainDB(sql.getConnection());
        this.guildManager = new GuildManager(playerGuildDB, instance);
        this.playerGuildManager = new PlayerGuildManager(playerGuildDB, guildManager, instance);
        this.inviteManager = new InviteManager(playerGuildManager);
        this.messages = new MessagesConfig(instance, "messages");
        this.guildConfig = new GuildConfig(instance, "guild");
    }

    public void setupSql(){
        Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
            playerGuildDB.createPlayerGuildTable();
            playerGuildDB.createGuildTable();
            playerGuildDB.createMembersTable();
            playerGuildDB.createAlliesTable();
            playerGuildDB.createEnemiesTable();
        });
    }

    public void loadPlayerData(){
        for (Player p: Bukkit.getOnlinePlayers()){
            playerGuildManager.loadPlayerGuild(p);
            guildManager.loadGuild(playerGuildManager.getPlayerGuildUUID(p));
        }
    }

    public void savePlayerData(){
        for (Player p: Bukkit.getOnlinePlayers()){
            playerGuildManager.savePlayerGuild(p);
            guildManager.saveGuild(playerGuildManager.getPlayerGuildUUID(p));
        }
    }

    public GuildConfig getGuildConfig() {
        return guildConfig;
    }
    public InviteManager getInviteManager() {
        return inviteManager;
    }

    public PlayerGuildManager getPlayerGuildManager() {
        return playerGuildManager;
    }

    public SQLite getSqLite() {
        return sqLite;
    }

    public MySQL getSql() {
        return sql;
    }

    public MainDB getPlayerGuildDB() {
        return playerGuildDB;
    }

    public GuildManager getGuildManager() {
        return guildManager;
    }

    public MessagesConfig getMessages() {
        return messages;
    }
}
