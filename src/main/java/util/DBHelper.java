package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cozyloon.EzConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {
    private static final String SQL_ERROR = "SQL Error";
    private HikariDataSource dataSource;

    public DBHelper(String dbUrl, String dbUserName, String dbPassword) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUserName);
        config.setPassword(dbPassword);
        config.setMaximumPoolSize(10);
        config.setMaxLifetime(1800000);
        config.setAutoCommit(false);

        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    public void executeQuery(String query) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            EzConfig.logERROR(SQL_ERROR, e);
        } finally {
            executeDBPostStepsWithLoggers(null, connection, preparedStatement);
        }
    }

    public void executeDBPostStepsWithLoggers(ResultSet resultSet, Connection connection, PreparedStatement preparedStatement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                EzConfig.logERROR(SQL_ERROR, e);
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                EzConfig.logERROR(SQL_ERROR, e);
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                EzConfig.logERROR(SQL_ERROR, e);
            }
        }
    }

    public void insertDataToDB() {
        String query = "INSERT INTO <table> (CustomerName, ContactName, Address, City, PostalCode, Country)\n" +
                "VALUES ('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', 'Norway');";
        executeQuery(query);
        EzConfig.logINFO("Data inserted");
    }

    public void deleteDataFromDB() {
        String query = "DELETE FROM <table> WHERE CustomerName='Alfreds Futterkiste';";
        executeQuery(query);
        EzConfig.logINFO("Data deleted");
    }


}
