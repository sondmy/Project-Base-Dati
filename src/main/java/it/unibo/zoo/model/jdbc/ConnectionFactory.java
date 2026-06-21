package it.unibo.zoo.model.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import it.unibo.zoo.utils.EnvLoader;

public final class ConnectionFactory {

    private static final ConnectionFactory INSTANCE = new ConnectionFactory();

    private final String host;
    private final String port;
    private final String database;
    private final String user;
    private final String password;

    private ConnectionFactory() {
        this.host = EnvLoader.get("POSTGRES_HOST");        
        this.port = EnvLoader.get("POSTGRES_PORT");
        this.database = EnvLoader.get("POSTGRES_DB");
        this.user = EnvLoader.get("POSTGRES_USER");
        this.password = EnvLoader.get("POSTGRES_PASSWORD");

    }

    public static ConnectionFactory getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://" + host + ':' + port + '/' + database;
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println("OK CONNESSO");
        return conn;
    }
}
