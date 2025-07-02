package com.chuanzhi.health.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBUtil {

    private static HikariDataSource dataSource;

    static {
        InputStream inputStream = null;
        try {
            Properties props = new Properties();
            inputStream = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            if (inputStream == null) {
                throw new RuntimeException("Cannot find db.properties in classpath");
            }
            props.load(inputStream);

            HikariConfig config = new HikariConfig();
            config.setDriverClassName(props.getProperty("jdbc.driver"));
            config.setJdbcUrl(props.getProperty("jdbc.url"));
            config.setUsername(props.getProperty("jdbc.username"));
            config.setPassword(props.getProperty("jdbc.password"));

            // Optional connection pool settings
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);

            dataSource = new HikariDataSource(config);
        } catch (IOException e) {
            throw new RuntimeException("Could not load db.properties", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // log or ignore
                }
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}