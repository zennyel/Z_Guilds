package com.zennyel.sao.database.type;

import com.zennyel.sao.Z_Guilds;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite extends Database {

    private Connection connection;
    private Z_Guilds instance;
    private ConsoleCommandSender sender;

    public SQLite(Z_Guilds instance) {
        this.instance = instance;
        this.sender = Bukkit.getConsoleSender();
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection(){
        if(!instance.getDataFolder().exists()){
            instance.getDataFolder().mkdir();
        }

        File dataFolder = new File(instance.getDataFolder(), "sqlite.db");

        if(!dataFolder.exists()){
            try {
                dataFolder.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
                sender.sendMessage("§cOcorreu um erro ao criar o arquivo do banco de dados: " + e.getCause());
                return null;
            }
        }
            try {
                if(connection == null || connection.isClosed()) {
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
                    return connection;
                }
            }catch (SQLException | ClassCastException | ClassNotFoundException e){
                sender.sendMessage("§cOcorreu um erro ao estabelecer a conexão sqlite!");
            }
            return null;
    }

    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                Bukkit.getConsoleSender().sendMessage("§a§lDisconnecting of database!");
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§a§lDisconnecting of database!");
        }
    }

}
