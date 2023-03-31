package com.zennyel.sao.database.type;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends Database {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private String table;

    private SQLite sqLite;
    private ConsoleCommandSender sender;

    private Connection connection;

    public MySQL(FileConfiguration config, SQLite sqLite) {
        this.host = config.getString("MySQL.host");
        this.port = config.getInt("MySQL.port");
        this.database = config.getString("MySQL.database");
        this.username = config.getString("MySQL.username");
        this.password = config.getString("MySQL.password");
        this.table = config.getString("MySQL.table");
        this.sqLite = sqLite;
        this.sender = Bukkit.getConsoleSender();
    }

    public Connection getConnection() {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url);
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
        } catch (ClassNotFoundException | SQLException e) {
            sender.sendMessage("Failed to connect to MySQL, attempting to connect to SQLite...");
            connection = sqLite.getConnection();
            if (connection != null) {
                sender.sendMessage("Connected to SQLite successfully!");
                return connection;
            } else {
                sender.sendMessage("Failed to connect to SQLite, plugin may not work correctly!");
            }
        }
        return null;
    }

    public SQLite getSqLite() {
        return sqLite;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}