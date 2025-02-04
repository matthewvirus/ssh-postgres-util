package by.matthewvirus.jdbc;

import javax.sql.DataSource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public record CuposDataSource(String driver, String url, String name, String password) implements DataSource {

    private static volatile CuposDataSource instance;

    public CuposDataSource(String driver, String url, String name, String password) {
        this.driver = driver;
        this.url = url;
        this.name = name;
        this.password = password;
        instance = this;
    }

    public static CuposDataSource getInstance() {
        if (instance == null) {
            synchronized (CuposDataSource.class) {
                if (instance == null) {
                    try {
                        Properties properties = new Properties();
                        properties.load(CuposDataSource.class.getClassLoader().getResourceAsStream("dev.properties"));
                        instance = new CuposDataSource(
                                properties.getProperty("postgres.driver"),
                                properties.getProperty("postgres.urlCupos"),
                                properties.getProperty("postgres.name"),
                                properties.getProperty("postgres.password")
                        );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = new CustomConnector().getConnection(url, name, password);
        System.out.println(connection.getMetaData().getDatabaseProductVersion());
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection connection = new CustomConnector().getConnection(url, username, password);
        System.out.println(connection.getMetaData().getDatabaseProductVersion());
        return connection;
    }

    public Connection getConnection(String url, String username, String password) throws SQLException {
        Connection connection = new CustomConnector().getConnection(url, username, password);
        System.out.println(connection.getMetaData().getDatabaseProductVersion());
        return connection;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new SQLException();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new SQLException();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new SQLException();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new SQLException();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLException();
    }
}