package com.zennyel.sao.database;

import com.zennyel.sao.objects.guild.Guild;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainDB {

    private Connection connection;

    public MainDB(Connection connection) {
        this.connection = connection;
    }

    public void createPlayerGuildTable(){
        String sql = "CREATE TABLE IF NOT EXISTS " +
                "player_guild(" +
                "player_id VARCHAR(36) PRIMARY KEY NOT NULL," +
                "guild_id VARCHAR(36));";
        try( PreparedStatement stm = getConnection().prepareStatement(sql)){
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertPlayerGuild(String playerId, String guildId) {
        String sql = "INSERT INTO player_guild (player_id, guild_id) VALUES (?, ?)";
        try( PreparedStatement stm = getConnection().prepareStatement(sql)){
            stm.setString(1, playerId);
            stm.setString(2, guildId);
            stm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getPlayerGuild(String playerId) {
        String sql = "SELECT guild_id FROM player_guild WHERE player_id = ?";
        try( PreparedStatement stm = getConnection().prepareStatement(sql)){
            stm.setString(1, playerId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString("guild_id");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public void createGuildTable() {
        try (
                PreparedStatement statement = getConnection().prepareStatement(
                        "CREATE TABLE IF NOT EXISTS guilds (" +
                                "id VARCHAR(36) PRIMARY KEY," +
                                "name VARCHAR(255) NOT NULL," +
                                "owner_uuid VARCHAR(36) NOT NULL," +
                                "home_world VARCHAR(255) NOT NULL," +
                                "home_x DOUBLE NOT NULL," +
                                "home_y DOUBLE NOT NULL," +
                                "home_z DOUBLE NOT NULL," +
                                "home_yaw FLOAT NOT NULL," +
                                "home_pitch FLOAT NOT NULL" +
                                ");")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createMembersTable() {
        try (  PreparedStatement statement = getConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS guild_members (" +
                        "id VARCHAR(36) NOT NULL," +
                        "player_uuid VARCHAR(36) NOT NULL," +
                        "PRIMARY KEY (id, player_uuid)" +
                        ");")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAlliesTable() {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "CREATE TABLE IF NOT EXISTS guild_allies (" +
                                "id VARCHAR(36) NOT NULL," +
                                "ally_id VARCHAR(36) NOT NULL," +
                                "PRIMARY KEY (id, ally_id)" +
                                ");")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createEnemiesTable() {
        try (
                PreparedStatement statement = getConnection().prepareStatement(
                        "CREATE TABLE IF NOT EXISTS guild_enemies (" +
                                "id VARCHAR(36) NOT NULL," +
                                "enemy_id VARCHAR(36) NOT NULL," +
                                "PRIMARY KEY (id, enemy_id)" +
                                ");")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertGuild(String name, UUID id, Location home) {
        try (
                PreparedStatement statement = getConnection().prepareStatement(
                        "INSERT INTO guilds (id, name, owner_uuid, home_world, home_x, home_y, home_z, home_yaw, home_pitch) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                                "ON DUPLICATE KEY UPDATE " +
                                "name = VALUES(name), " +
                                "owner_uuid = VALUES(owner_uuid), " +
                                "home_world = VALUES(home_world), " +
                                "home_x = VALUES(home_x), " +
                                "home_y = VALUES(home_y), " +
                                "home_z = VALUES(home_z), " +
                                "home_yaw = VALUES(home_yaw), " +
                                "home_pitch = VALUES(home_pitch);")) {
            statement.setString(1, id.toString());
            statement.setString(2, name);
            statement.setString(3, home.getWorld().getName());
            statement.setDouble(4, home.getX());
            statement.setDouble(5, home.getY());
            statement.setDouble(6, home.getZ());
            statement.setFloat(7, home.getYaw());
            statement.setFloat(8, home.getPitch());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertGuildMembers(UUID id, List<Player> members){
        String sql = "INSERT INTO members(guild_id, player_uuid) VALUES (?, ?)";
        try (
                PreparedStatement statement = getConnection().prepareStatement(sql)) {
            for (Player member : members) {
                statement.setString(1, id.toString());
                statement.setString(2, member.getUniqueId().toString());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertGuildAllies(UUID id, List<Guild> allies){
        String sql = "INSERT INTO allies(guild_id, ally_id) VALUES (?, ?)";
        try (
                PreparedStatement statement = getConnection().prepareStatement(sql)) {
            for (Guild ally : allies) {
                statement.setString(1, id.toString());
                statement.setString(2, ally.getGuildId().toString());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertGuildEnemies(UUID id, List<Guild> enemies){
        String sql = "INSERT INTO enemies(guild_id, enemy_id) VALUES (?, ?)";
        try (
                PreparedStatement statement = getConnection().prepareStatement(sql)) {
            for (Guild enemy : enemies) {
                statement.setString(1, id.toString());
                statement.setString(2, enemy.getGuildId().toString());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGuild(UUID uuid){
        String sql = "DELETE FROM guilds WHERE guild_id = ?";
        try (
                PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Guild getGuild(UUID uuid){
        String sql = "SELECT * FROM guilds WHERE guild_id = ?";
        try (
                PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                UUID guildId = UUID.fromString(resultSet.getString("guild_id"));
                String ownerUuidString = resultSet.getString("owner_uuid");
                Location home = new Location(
                        Bukkit.getWorld(resultSet.getString("world_name")),
                        resultSet.getDouble("home_x"),
                        resultSet.getDouble("home_y"),
                        resultSet.getDouble("home_z")
                );
                List<Guild> allies = getGuildAllies(guildId);
                List<Guild> enemies = getGuildEnemies(guildId);
                List<Player> members = getGuildMembers(guildId);
                Guild guild = new Guild(name, Bukkit.getPlayer(ownerUuidString), home, allies, enemies, members);
                guild.setGuildId(guildId);
                return guild;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Guild> getGuildAllies(UUID guildId) {
        List<Guild> allies = new ArrayList<>();
        String sql = "SELECT * FROM allies WHERE guild_id = ?";
        try (
                PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, guildId.toString());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                UUID allyId = UUID.fromString(result.getString("ally_id"));
                Guild ally = getGuild(allyId);
                if (ally != null) {
                    allies.add(ally);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allies;
    }

    public List<Guild> getGuildEnemies(UUID guildId) {
        List<Guild> enemies = new ArrayList<>();
        String sql = "SELECT * FROM enemies WHERE guild_id = ?";
        try (
                PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, guildId.toString());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                UUID enemyId = UUID.fromString(result.getString("enemy_id"));
                Guild enemy = getGuild(enemyId);
                if (enemy != null) {
                    enemies.add(enemy);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enemies;
    }

    public List<Player> getGuildMembers(UUID guildId) {
        List<Player> members = new ArrayList<>();
        String sql = "SELECT * FROM members WHERE guild_id = ?";
        try (
                PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, guildId.toString());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                UUID playerId = UUID.fromString(result.getString("player_id"));
                Player player = Bukkit.getPlayer(playerId);
                if (player != null) {
                    members.add(player);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public Connection getConnection() {
        return connection;
    }
}
