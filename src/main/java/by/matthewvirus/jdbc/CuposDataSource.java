package by.matthewvirus.jdbc;

import javax.sql.DataSource;

import by.matthewvirus.model.db_user.DbUserData;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

@Getter
@Setter
public class CuposDataSource implements DataSource {

    private static volatile CuposDataSource instance;
    private final String driver;
    private final String url;
    private final String name;
    private final String password;


    public CuposDataSource(String driver, String url, String name, String password) {
        this.driver = driver;
        this.url = url;
        this.name = name;
        this.password = password;
        instance = this;
    }

    public CuposDataSource(DbUserData dbUserData) {
        this.driver = dbUserData.getDriverPath();
        this.name = dbUserData.getUsername();
        this.password = dbUserData.getPassword();
        this.url = dbUserData.getCuposURL();
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

    public String driver() {
        return driver;
    }

    public String url() {
        return url;
    }

    public String name() {
        return name;
    }

    public String password() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CuposDataSource) obj;
        return Objects.equals(this.driver, that.driver) &&
                Objects.equals(this.url, that.url) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driver, url, name, password);
    }

    @Override
    public String toString() {
        return "CustomDataSource[" +
                "driver=" + driver + ", " +
                "url=" + url + ", " +
                "name=" + name + ", " +
                "password=" + password + ']';
    }

}