package com.zennyel.sao.database.type;

import java.sql.Connection;

public abstract class Database {

    private Connection connection;


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
