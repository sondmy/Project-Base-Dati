package it.unibo.zoo.model.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionFactory {

    private static final ConnectionFactory INSTANCE = new ConnectionFactory();

    private final String host;
    private final String port;
    private final String database;
    private final String user;
    private final String password;

    private ConnectionFactory() {
        this.host = readEnv("ZOO_DB_HOST", "localhost");
        this.port = readEnv("ZOO_DB_PORT", "5432");
        this.database = readEnv("ZOO_DB_NAME", "zoo_gestionale");
        this.user = readEnv("ZOO_DB_USER", "zoo_user");
        this.password = readEnv("ZOO_DB_PASSWORD", "zoo_password_sicura");
    }

    public static ConnectionFactory getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://" + host + ':' + port + '/' + database;
        return DriverManager.getConnection(url, user, password);
    }

    private static String readEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
