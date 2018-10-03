package mjaroslav.bots.core.amadeus.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mjaroslav.bots.core.amadeus.AmadeusCore;

public class DefaultDatabaseHandler extends DatabaseHandler {
    private Connection connection;
    private Statement statement;
    
    public DefaultDatabaseHandler(String name, AmadeusCore core) {
        super(name, core);
    }

    @Override
    public boolean initDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + getFile().getAbsolutePath());
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void executeUpdate(String request) {
        try {
            getStatement().executeUpdate(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet executeQuery(String request) {
        try {
            return getStatement().executeQuery(request);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Statement getStatement() {
        if(statement == null)
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return statement;
    }

    @Override
    public void close() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}